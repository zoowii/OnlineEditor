<g:applyLayout name="main">
    <div class="panel panel-primary">
        <div class="panel-heading">Change Password</div>

        <div class="panel-body">
            <form class="form-horizontal" role="form" action="<g:createLink action="doChangePassword"/>"
                  method="POST">
                <div class="form-group">
                    <label class="col-sm-2 control-label">Old Password</label>

                    <div class="col-sm-10">
                        <input type="password" name="old_password" class="form-control" placeholder="Old Password"
                               required="">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">New Password</label>

                    <div class="col-sm-10">
                        <input type="password" name="new_password" class="form-control" placeholder="New Password"
                               required="">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Change Password</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

</g:applyLayout>