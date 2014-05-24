<g:applyLayout name="main">
    <div class="row main-content">
        <div>
            <a href="<g:createLink action="createPage"/>" class="btn btn-primary btn-sm">New Bucket</a>
            <hr/>
        </div>

        <div>
            <table class="table table-bordered table-stripped">
                <thead>
                <th>ID</th>
                <th>Name</th>
                <th>Created Time</th>
                <th></th>
                </thead>
                <tbody>
                <g:each in="${buckets}" var="bucket" status="counter">
                    <tr>
                        <td>${bucket.id}</td>
                        <td>${bucket.name}</td>
                        <td>${bucket.createdTime}</td>
                        <td>
                            <span class="glyphicon glyphicon-trash"></span>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            ${raw(paginator.getSimplePaginationHtml())}
        </div>
    </div>
</g:applyLayout>