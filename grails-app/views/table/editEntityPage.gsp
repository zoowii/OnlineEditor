<g:applyLayout name="main">
    <h4 style="text-align: center">
        <g:if test="${isNew}">
            Create Entity in table ${table.name}
        </g:if>
        <g:if test="${!isNew}">
            Edit Entity ${entity.id} in table ${table.name}
        </g:if>
    </h4>

    <h4>Columns</h4>

    <div>
        <table class="table table-bordered table-stripped">
            <thead>
            <th></th>
            <th>Name</th>
            <th>Type</th>
            <th>Value</th>
            <th></th>
            </thead>
            <tbody>
            <g:each in="${entity.columns}" var="col" status="counter">
                <tr>
                    <td>${counter}</td>
                    <td>${col.name}</td>
                    <td>${col.type}</td>
                    <td>${col.value}</td>
                    <td>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span class="glyphicon glyphicon-trash"></span>
                    </td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div>
    <g:if test="${!isNew}">
        <h4>Create Column</h4>

        <form class="form-horizontal" role="form" method="POST"
              action="<g:createLink action="createOrUpdateEntityColumn"/>">
            <input type="hidden" name="entityId" value="${entity?.id}"/>
            <input type="hidden" name="columnId" value="${newColumn.id}"/>

            <div class="form-group">
                <label class="col-sm-2 control-label">Name</label>

                <div class="col-sm-10">
                    <input type="text" name="name" class="form-control" value="${newColumn.name}" placeholder="Name" required="">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">Type</label>

                <div class="col-sm-10">
                    <select name="type" class="form-control">
                        <g:each in="${columnTypes}" var="columnType">
                            <g:if test="${newColumn.type == columnType[0]}">
                                <option value="${columnType[0]}" selected="selected">${columnType[1]}</option>
                            </g:if>
                            <g:else>
                                <option value="${columnType[0]}">${columnType[1]}</option>
                            </g:else>
                        </g:each>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">Value</label>

                <div class="col-sm-10">
                    <input type="text" name="value" class="form-control" value="${newColumn.value}" placeholder="Value" required="">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default">Submit</button>
                </div>
            </div>
        </form>
    </g:if>

</g:applyLayout>