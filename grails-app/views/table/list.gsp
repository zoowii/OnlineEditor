<g:applyLayout name="main">
    <div class="row main-content">
        <div>
            <a href="<g:createLink action="createPage"/>" class="btn btn-primary btn-sm">New Table</a>
            <hr/>
        </div>

        <div>
            <table class="table table-bordered table-stripped">
                <thead>
                <th>ID</th>
                <th>Name</th>
                <th>Total Count</th>
                <th>Created Time</th>
                <th></th>
                </thead>
                <tbody>
                <g:each in="${tables}" var="table" status="counter">
                    <tr>
                        <td>${table.id}</td>
                        <td>${table.name}</td>
                        <td>${table.createdTime}</td>
                        <td>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="#"
                               class="glyphicon glyphicon-resize-full"></a>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="<g:createLink action="editPage" params="[id: table.id]"/>"
                               class="glyphicon glyphicon-pencil"></a>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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