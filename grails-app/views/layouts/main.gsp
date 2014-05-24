<g:applyLayout name="website">
    <div class="blog-masthead">
        <div class="container">
            <nav class="blog-nav">
                <a class="blog-nav-item active" href="<g:createLink controller="site" action="index"/>">Home</a>
                <a class="blog-nav-item" href="<g:createLink controller="file" action="create"/>">New File</a>
                <a class="blog-nav-item" href="<g:createLink controller="site" action="profile"/>">Profile</a>
                <!--<a class="blog-nav-item" href="#">Account Settings</a>-->
                <!--<a class="blog-nav-item" href="#">Administration</a>-->
                <a class="blog-nav-item" href="#">About</a>
                <g:if test="${!session.username}">
                    <a class="blog-nav-item" href="<g:createLink controller="site" action="register"/>">Sign Up</a>
                    <a class="blog-nav-item" href="<g:createLink controller="site" action="loginPage"/>">Sign In</a>
                </g:if>
                <g:if test="${session.username}">
                    <a class="blog-nav-item" href="<g:createLink controller="site" action="logout"/>">Sign Out</a>
                    <a class="blog-nav-item" href="<g:createLink controller="site" action="profile"/>"
                       style="float: right">Welcome, ${session.username}</a>
                </g:if>
            </nav>
        </div>
    </div>

    <div class="container" style="margin-top: 30px">
        <div>
            <g:if test="${flash.success}">
                <div class="alert alert-success">${flash.success}</div>
            </g:if>
            <g:if test="${flash.info}">
                <div class="alert alert-info">${flash.info}</div>
            </g:if>
            <g:if test="${flash.error}">
                <div class="alert alert-danger">${flash.error}</div>
            </g:if>
            <g:if test="${flash.warning}">
                <div class="alert alert-warning">${flash.warning}</div>
            </g:if>
        </div>
        <g:layoutBody/>
    </div>

    <div class="blog-footer" role="contentinfo">
        <p>Author: <a href="http://zoowii.com" target="_blank">@zoowii</a></p>
    </div>
</g:applyLayout>
