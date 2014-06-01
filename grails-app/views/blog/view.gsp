<g:applyLayout name="blogsite">
    <title>${blogTitle}</title>
    <asset:stylesheet src="blog/article.css"/>
    <h1><a href="<g:createLink action="view" params="[id: article?.id]"/>">${article?.title}</a></h1>
    <blockquote>
        <p>
            <g:each in="${article?.tags}" var="tag">
                <span class="label label-primary">${tag}</span>
            </g:each>
        </p>

        <p>${article.summary}</p>

        <div class="blog-author-area">
            <span class="blog-author-field">Author: <a href="${author?.url}">${article.author?.aliasName}</a></span>
            &nbsp;&nbsp;&nbsp;
            <span class="blog-date-field">${article?.date?.format('yyyy-MM-dd')}</span>
        </div>
    </blockquote>

    <div>
        ${raw(article.html)}
    </div>
    <hr/>

    <div>
        <!-- 评论区域 TODO: 不要硬编码,外部注入 -->
        <!-- 多说评论框 start -->
        <div class="ds-thread" data-thread-key="请将此处替换成文章在你的站点中的ID" data-title="请替换成文章的标题" data-url="请替换成文章的网址"></div>
        <!-- 多说评论框 end -->
        <!-- 多说公共JS代码 start (一个网页只需插入一次) -->
        <script type="text/javascript">
            var duoshuoQuery = {short_name: "${metaInfo.duoshuo_username}"}; // 使用当前作者
            (function () {
                var ds = document.createElement('script');
                ds.type = 'text/javascript';
                ds.async = true;
                ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
                ds.charset = 'UTF-8';
                (document.getElementsByTagName('head')[0]
                        || document.getElementsByTagName('body')[0]).appendChild(ds);
            })();
        </script>
        <!-- 多说公共JS代码 end -->
    </div>
</g:applyLayout>