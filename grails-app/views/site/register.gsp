<g:applyLayout name="main">
    <form class="form-horizontal" role="form" action="<g:createLink action="doRegister"/>" method="POST">
        <div class="form-group">
            <label class="col-sm-2 control-label">Username</label>

            <div class="col-sm-10">
                <input type="text" name="username" class="form-control" placeholder="Username" required="">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Email</label>

            <div class="col-sm-10">
                <input type="text" name="email" class="form-control" placeholder="Email" required="">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Password</label>

            <div class="col-sm-10">
                <input type="password" name="password" class="form-control" placeholder="Password" required="">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Sign Up</button>
            </div>
        </div>
    </form>
</g:applyLayout>