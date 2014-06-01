<g:applyLayout name="main">
    <title>Blog Admin</title>

    <div class="row main-content">
        <div>
            <a class="btn btn-primary"
               href="<g:createLink controller="file" action="createInBucket"
                                   params="[bucketName: bucket.name]"/>">Post Article</a>
        </div>

        <div>
            <h4>Blog Meta</h4>
            <a href="<g:createLink controller="table" action="editEntityPage"
                                   params="[tableId: metaInfo.tableId, entityId: metaInfo.id]"/>"
               class="btn btn-primary btn-sm">Edit Blog Meta</a>
            <table class="table table-bordered table-stripped">
                <tbody>
                <tr>
                    <td style="width: 50%">Blog Title(site_title)</td>
                    <td>${metaInfo['site_title']}</td>
                </tr>
                <tr>
                    <td>Sub Title(sub_title)</td>
                    <td>${metaInfo['sub_title']}</td>
                </tr>
                <tr>
                    <td>Blog Host(blog_host)</td>
                    <td>${metaInfo['blog_host']}</td>
                </tr>
                <tr>
                    <td>Duoshuo Username(duoshuo_username)</td>
                    <td>${metaInfo['duoshuo_username']}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div>
            <h4>OutLinks</h4>
            <a class="btn btn-primary btn-sm"
               href="<g:createLink controller="table" action="listEntities"
                                   params="[id: outLinksTable.id]"/>">Edit OutLinks(url, name, title)</a>
        </div>

        <div>
            <h4>Articles</h4>
            <table class="table table-bordered table-stripped">
                <thead>
                <th>ID</th>
                <th>Title</th>
                <th>Tags</th>
                <th>Date</th>
                <th>Created Time</th>
                <th></th>
                </thead>
                <tbody>
                <g:each in="${articles}" var="article">
                    <tr>
                        <td>${article.id}</td>
                        <td>${article.title}</td>
                        <td>${article.tags?.join(', ')}</td>
                        <td>${article.date?.format('yyyy-MM-dd')}</td>
                        <td>${article.createdTime?.format('yyyy-MM-dd')}</td>
                        <td>
                            <a href="<g:createLink controller="file" action="edit"
                                                   params="[id: article.id]"/>">Edit Article</a>
                            <br/>
                            <a href="<g:createLink controller="blog" action="edit"
                                                   params="[id: article.id]"/>">Edit Meta</a>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            ${raw(paginator.getSimplePaginationHtml())}
        </div>
    </div>
</g:applyLayout>