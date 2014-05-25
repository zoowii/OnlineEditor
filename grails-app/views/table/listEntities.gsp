<g:applyLayout name="main">
    <div class="row main-content">
        <div>
            <h4>Table: ${table.name}</h4>
            <a href="<g:createLink action="createEntityPage" params="[tableId: table.id]"/>"
               class="btn btn-primary btn-sm">New Entity</a>
            <hr/>
        </div>

        <div>
            <table class="table table-bordered table-stripped">
                <thead>
                <th>ID</th>
                <th>Created Time</th>
                <th></th>
                </thead>
                <tbody>
                <g:each in="${entities}" var="entity" status="counter">
                    <tr>
                        <td>${entity.id}</td>
                        <td>${table.createdTime}</td>
                        <td>
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="#"
                               class="glyphicon glyphicon-resize-full"></a>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="<g:createLink action="editEntityPage"
                                                   params="[tableId: table.id, entityId: entity.id]"/>"
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