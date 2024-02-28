package com.binbin.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
//自定义事件类，包含事件相关的数据
public class ReadArticleEvent extends ApplicationEvent {

    /**
     * 文章 ID
     */
    private Long articleId;

    //source发起事件的对象，articleId事件相关的数据
    public ReadArticleEvent(Object source, Long articleId) {//有参构造器
        super(source); //调用父类 ApplicationEvent 的构造器
        this.articleId = articleId;
    }

}
