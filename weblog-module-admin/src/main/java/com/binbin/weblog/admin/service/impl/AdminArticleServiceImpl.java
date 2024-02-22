package com.binbin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.binbin.weblog.admin.model.vo.article.DeleteArticleReqVO;
import com.binbin.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.binbin.weblog.common.domain.dos.*;
import com.binbin.weblog.common.domain.mapper.*;
import com.binbin.weblog.common.enums.ResponseCodeEnum;
import com.binbin.weblog.common.exception.BizException;
import com.binbin.weblog.common.utils.Response;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    /**
     * 发布文章
     */
    @Override//通过事务管理，确保这些操作要么全部成功，要么在出现异常时全部回滚
    @Transactional(rollbackFor = Exception.class)//rollbackFor = Exception.class 表示任何异常都会触发事务回滚
    public Response publishArticle(PublishArticleReqVO publishArticleReqVO) {
//-----------------------------------处理一对多，文章（ArticleDO）与文章内容（ArticleContentDO）------------------------------------------
        // 1. VO 转 ArticleDO, 并保存
        ArticleDO articleDO = ArticleDO.builder()
                .title(publishArticleReqVO.getTitle())
                .cover(publishArticleReqVO.getCover())
                .summary(publishArticleReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        articleMapper.insert(articleDO);
        // 拿到插入记录的文章的主键 id
        Long articleId = articleDO.getId();
        // 2. VO 转 ArticleContentDO，并保存
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
        //子表 t_article_content 中 articleId作外键，引用了主表 t_article 的主键 id。后续通过外键在 t_article_content 表中找到所有与该文章相关的内容条目
                .articleId(articleId)
                .content(publishArticleReqVO.getContent())
                .build();
        articleContentMapper.insert(articleContentDO);
//------------------------------处理多对多，文章（ArticleDO）和分类（CategoryDO）----------------------------------------------------------
        // 3. 处理文章关联的分类
        Long categoryId = publishArticleReqVO.getCategoryId();

        // 3.1 校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                //两个外键字段，一个指向文章表的 id 字段，另一个指向分类表的 id 字段。
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 4. 保存文章关联的多个标签名集合
        List<String> publishTags = publishArticleReqVO.getTags();

        insertTags(articleId, publishTags);

        return Response.success();
    }


    /**
     * 保存标签，入参  "tags": ["8数据库已在", "9数据库已在", "前端新加标签1", "前端新加标签2"]
     */
    private void insertTags(Long articleId, List<String> publishTags) {
        // 筛选提交的标签（表中不存在的标签）
        List<String> notExistTags = null;
        // 筛选提交的标签（表中已存在的标签）
        List<String> existedTags = null;

        // null查询出所有标签，是数据库数据tagDOS
        List<TagDO> tagDOS = tagMapper.selectList(null);

        /*筛选已存在的标签  publishTags是前端标签名的集合,有数字,有文字。入参 "tags": ["8数据库已在", "9数据库已在", "前端新加标签1", "前端新加标签2"]
        已存在的标签，直接提交字符串 ID, 如 8、9;   而那些表中不存在的，则是标签字符串名称, 如 新的标签1 、 新的标签2 。*/
        if (CollectionUtils.isEmpty(tagDOS)) {
            notExistTags = publishTags; //数据库中的标签表是空时，用户提交的任意标签都是要入库的标签，所以无需进行复杂的筛选操作，直接认为所有提交的标签均为待插入的新标签即可。
        } else {
            //数据库表的标签集合，将所有标签的ID转换String.valueOf为字符串形式存入 tagIds 列表
            List<String> tagIds = tagDOS.stream().map(tagDO -> String.valueOf(tagDO.getId())).collect(Collectors.toList());
            // 通过标签 ID 来筛选，包含对应 ID 则表示提交的标签是表中存在的   filter该标签会被过滤器保留
            existedTags = publishTags.stream().filter(publishTag -> tagIds.contains(publishTag)).collect(Collectors.toList());
            // 否则是不存在的
            notExistTags = publishTags.stream().filter(publishTag -> !tagIds.contains(publishTag)).collect(Collectors.toList());
            /*还有一种可能：提交上来的标签，也有可能是表中已存在的，比如表中已经有了Java标签，用户提交了个 java 小写的标签，要toLowerCase()转小写
            collect方法将流的数据收集，通过Collectors.toMap生成一个新集合,键是标签名（全部转为小写），值是对应的标签ID
            List<TagDO>类型tagDOS列表 转换为 Map<String, Long>类型tagNameIdMap */
            Map<String, Long> tagNameIdMap = tagDOS.stream().collect(Collectors.toMap(tagDO -> tagDO.getName().toLowerCase(), TagDO::getId));

            // notExistTags 集合的迭代器，用于遍历集合中的元素，对不存在的标签记录 进行安全的删除操作
            Iterator<String> iterator = notExistTags.iterator();
            while (iterator.hasNext()) { // hasNext()到末尾，无剩余
                String notExistTag = iterator.next(); //获取 notExistTags 集合中的下一个 对象
                // 是否包含containsKey该Map键 小写标签名，则表示该新标签是重复标签
                if (tagNameIdMap.containsKey(notExistTag.toLowerCase())) {
                    //找到了重复的标签，执行以下操作： 迭代器中移除当前标签
                    iterator.remove();
                    //将.get（键）拿到 值 标签ID 转换String.valueOf字符串 添加到 已存在的标签集合existedTags
                    existedTags.add(String.valueOf(tagNameIdMap.get(notExistTag.toLowerCase())));
                }
            }
        }
//----------------- -分别对存在于数据库中的标签 (existedTags) 与不存在于数据库中的标签 (notExistTags) 进行处理----------------------------
        // 将提交的上来的，已存在于表中的标签——字符串的标签ID，数字。文章-标签关系表入库
        if (!CollectionUtils.isEmpty(existedTags)) {
            List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList(); //Lists是Guava库中工具类，newArrayList()创建一个不可变的列表。
            existedTags.forEach(tagId -> {
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(Long.valueOf(tagId))
                        .build();
                articleTagRelDOS.add(articleTagRelDO);//加入空列表
            });
            // 批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
        }

        // 将提交的上来的，不存在于表中的标签——字符串的标签名，文字。先入tag标签表保存
        if (!CollectionUtils.isEmpty(notExistTags)) {
            List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
            notExistTags.forEach(tagName -> {
                TagDO tagDO = TagDO.builder()
                        .name(tagName)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();

                tagMapper.insert(tagDO);
            //要先入tag表保存，拿到对应标签 ID 后，再把文章-标签关联关系入库
                Long tagId = tagDO.getId();

                // 文章-标签关联关系
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(tagId)
                        .build();
                articleTagRelDOS.add(articleTagRelDO);//加入空列表
            });
            // 批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
        }
    }

    /**
     * 删除文章
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO) {
        Long articleId = deleteArticleReqVO.getId();

        // 1. 删除文章
        articleMapper.deleteById(articleId);
        // 2. 删除文章内容
        articleContentMapper.deleteByArticleId(articleId);
        // 3. 删除文章-分类关联记录
        articleCategoryRelMapper.deleteByArticleId(articleId);
        // 4. 删除文章-标签关联记录
        articleTagRelMapper.deleteByArticleId(articleId);
        return Response.success();
    }

}

