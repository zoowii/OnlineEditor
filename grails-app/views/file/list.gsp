<g:applyLayout name="main">
    <div class="row main-content">
        <div>
            <a href="<g:createLink action="create"/>" class="btn btn-primary btn-sm">New File</a>
            <hr/>
        </div>

        <div>
            <table class="table table-bordered table-stripped">
                <thead>
                <th>ID</th>
                <th>Name</th>
                <th>Bucket</th>
                <th>Created Time</th>
                <th></th>
                </thead>
                <tbody>
                <g:each in="${files}" var="file" status="counter">
                    <tr>
                        <td>${file.id}</td>
                        <td>${file.name}</td>
                        <td>${file.bucket.name}</td>
                        <td>${file.createdTime}</td>
                        <td>
                            &nbsp;&nbsp;&nbsp;
                            <a href="<g:createLink action="edit" params="[id: file.id]"/>"
                               class="glyphicon glyphicon-pencil"></a>
                            &nbsp;&nbsp;&nbsp;
                            &nbsp;&nbsp;&nbsp;
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