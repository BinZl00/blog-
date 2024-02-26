package com.binbin.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.binbin.weblog.common.domain.dos.ArticleDO;
import com.binbin.weblog.common.domain.mapper.ArticleMapper;
import com.binbin.weblog.common.utils.PageResponse;
import com.binbin.weblog.common.utils.Response;
import com.binbin.weblog.web.convert.ArticleConvert;
import com.binbin.weblog.web.model.vo.archive.FindArchiveArticlePageListReqVO;
import com.binbin.weblog.web.model.vo.archive.FindArchiveArticlePageListRspVO;
import com.binbin.weblog.web.model.vo.archive.FindArchiveArticleRspVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取文章归档分页数据
     */
    @Override
    public Response findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO) {
        Long current = findArchiveArticlePageListReqVO.getCurrent(); //当前页码
        Long size = findArchiveArticlePageListReqVO.getSize(); //每页显示的数量

        // 分页查询
        IPage<ArticleDO> page = articleMapper.selectPageList(current, size, null, null, null);
        List<ArticleDO> articleDOS = page.getRecords();  //获取当前页的文章记录列表

        List<FindArchiveArticlePageListRspVO> vos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(articleDOS)) {
            // DO 转 文章列表子类VO
            List<FindArchiveArticleRspVO> archiveArticleRspVOS =  articleDOS.stream()
                    .map(articleDO -> ArticleConvert.INSTANCE.convertDO2ArchiveArticleVO(articleDO))
                    .collect(Collectors.toList());

            /*按创建的月份进行分组
            Collectors.groupingBy快速得到一个Map，earMonth 类型的 createMonth 作为键，对应的值是包含具有相同 createMonth 的 FindArchiveArticleRspVO 对象的列表*/
            Map<YearMonth, List<FindArchiveArticleRspVO>> map = archiveArticleRspVOS.stream().collect(Collectors.groupingBy(FindArchiveArticleRspVO::getCreateMonth));
            //TreeMap是一个有序的 Map，Collections.reverseOrder降序排列，即最新的月份（即数值最大的月份）会排在前面
            Map<YearMonth, List<FindArchiveArticleRspVO>> sortedMap = new TreeMap<>(Collections.reverseOrder());
            sortedMap.putAll(map);//放进去
//---------------------上面子类FindArchiveArticleRspVO处理，下面主类FindArchiveArticlePageListRsp处理-------------------------------
           /*遍历排序后的 Map，转换为 主类文章按月归档 VO
            k表示每个键，类型为 YearMonth 年月，v每个对应值，是 List<FindArchiveArticleRspVO>，即每个月份对应的归档文章列表*/
            sortedMap.forEach((k, v) -> vos.add(FindArchiveArticlePageListRspVO.builder().month( k ).articles( v ).build()));
        }

        return PageResponse.success(page, vos);
    }
}
