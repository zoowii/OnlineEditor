<g:applyLayout name="main">
    <h4 style="text-align: center">
        <g:if test="${isNew}">
            Create Table
        </g:if>
        <g:if test="${!isNew}">
            Edit Table ${table?.name}
        </g:if>
    </h4>

    <form class="form-horizontal" role="form" method="POST"
          action="<g:createLink action="createOrUpdate" params="[id: table?.id]"/>">
        <input type="hidden" name="id" value="${table?.id}"/>

        <div class="form-group">
            <label class="col-sm-2 control-label">Name</label>

            <div class="col-sm-10">
                <input type="text" name="name" class="form-control" value="${table?.name}" placeholder="Name">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Submit</button>
            </div>
        </div>
    </form>

</g:applyLayout>