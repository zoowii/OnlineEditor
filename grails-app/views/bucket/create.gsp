<g:applyLayout name="main">
    <form class="form-horizontal" role="form" method="POST" action="<g:createLink action="create"/>">
        <div class="form-group">
            <label class="col-sm-2 control-label">Name</label>

            <div class="col-sm-10">
                <input type="text" name="name" class="form-control" placeholder="Name">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Submit</button>
            </div>
        </div>
    </form>

</g:applyLayout>