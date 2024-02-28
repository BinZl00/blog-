package com.binbin.weblog.web.markdown;

import com.binbin.weblog.web.markdown.provider.NofollowLinkAttributeProvider;
import com.binbin.weblog.web.markdown.renderer.ImageNodeRenderer;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

public class MarkdownHelper {

    /**
     * Markdown 解析器
     */
    private final static Parser PARSER;
    /**
     * HTML 渲染器
     */
    private final static HtmlRenderer HTML_RENDERER;

    /**
     * 静态初始化块，它在类加载时执行。创建了 Parser 和 HtmlRenderer 的实例。
     * 这些实例在整个应用程序中共享。
     */
    static {
        List<Extension> extensions = Arrays.asList(
                TablesExtension.create(),     // 表格拓展
                HeadingAnchorExtension.create(), // 标题锚定项
                ImageAttributesExtension.create(), // 图片宽高
                TaskListItemsExtension.create() // 任务列表
            );
        PARSER = Parser.builder().extensions(extensions).build();
        HTML_RENDERER = HtmlRenderer.builder().extensions(extensions)
                .attributeProviderFactory(context -> new NofollowLinkAttributeProvider())
                .nodeRendererFactory(context -> new ImageNodeRenderer(context))
                .build();
    }


    /**
     * 将 Markdown 转换成 HTML
     * @param markdown
     */
    public static String convertMarkdown2Html(String markdown) {
        Node document = PARSER.parse(markdown);
        return HTML_RENDERER.render(document);
    }

    public static void main(String[] args) {
        String markdown = "This is *测试*";
        System.out.println(MarkdownHelper.convertMarkdown2Html(markdown));
    }

}
