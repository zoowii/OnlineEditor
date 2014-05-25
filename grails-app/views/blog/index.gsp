<g:applyLayout name="blogsite">
    <title>${blogTitle}</title>
    <asset:stylesheet src="blog/article.css"/>
    <g:each in="${articles}" var="article" status="counter">
        <h1>${article?.title}</h1>
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
    </g:each>
</g:applyLayout>