package com.binbin.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_article_category_rel")  //文章-分类关系表
public class ArticleCategoryRelDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long articleId; // 关联的文章 ID

    private Long categoryId; //关联的分类 ID
}
/**
 | id | article_id | category_id |
 | 1  | 101        | 1          |
 | 2  | 101        | 2          |
 | 3  | 102        | 1          |
 只返回那些 category_id 等于 1 的记录，并且只返回一条记录（因为 LIMIT 1）
 SELECT * FROM t_article_category_rel WHERE category_id = 1 LIMIT 1;
 | id | article_id | category_id |
 | 1  | 101        | 1          |
 结果表示文章 ID 为 101 的文章与分类 ID 为 1 的分类有关联
 */