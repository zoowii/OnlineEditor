<!doctype html>
<html class="no-js">
<head>
    <meta charset="UTF-8">
    <title><g:layoutTitle default="Blog"/></title>

    <!--IE Compatibility modes-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <!--Mobile first-->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.jpg')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'favicon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'favicon.png')}">
    <asset:stylesheet src="bootstrap.min.css"/>
    <asset:stylesheet src="blog/font-awesome.min.css"/>
    <asset:stylesheet src="blog/main.min.css"/>
    <asset:stylesheet src="blog/theme.css"/>
    <!--[if lt IE 9]>
    <asset:javascript src="html5shiv.js"/>
    <asset:javascript src="respond.min.js"/>
    <![endif]-->

    <!--Modernizr 2.7.2-->
    <asset:javascript src="blog/modernizr.min.js"/>
</head>

<body>
<div id="wrap">
<div id="top">

    <!-- .navbar -->
    <nav class="navbar navbar-inverse navbar-static-top">
        <div class="container-fluid">

            <!-- Brand and toggle get grouped for better mobile display -->
            <header class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="${metaInfo.blog_host}" class="navbar-brand">
                    <asset:image src="blog/logo.png"/>
                </a>
            </header>

            <div class="collapse navbar-collapse navbar-ex1-collapse">

                <!-- .nav -->
                <ul class="nav navbar-nav">
                    <li><a href="${author.url}"><g:message code="blog.layout.aboutAuthor"/></a></li>
                    <li><a href="https://github.com/${author.userName}">Github</a></li>
                    <g:if test="${isAuthor}">
                        <li><a href="<g:createLink controller="site" action="index"/>">Dashboard</a></li>
                        <li><a href="<g:createLink controller="table"/>">Tables</a></li>
                        <li><a href="<g:createLink controller="file" action="list"/>">File Manager</a></li>
                    </g:if>
                </ul><!-- /.nav -->
            </div>

        </div><!-- /.container-fluid -->
    </nav><!-- /.navbar -->
    <header class="head">
        <div class="search-bar">
            <form class="main-search" action="">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Live Search ...">
                    <span class="input-group-btn">
                        <button class="btn btn-primary btn-sm text-muted" type="button">
                            <i class="fa fa-search"></i>
                        </button>
                    </span>
                </div>
            </form><!-- /.main-search -->
        </div><!-- /.search-bar -->
        <div class="main-bar">
            <h3>
                <i class="fa fa-home"></i>
                <a href="${metaInfo.blog_host}" style="color: #ffffff">${metaInfo.site_title}</a>

                <div class="g-plusone" data-annotation="inline" data-width="220"></div>

                <script type="text/javascript">
                    window.___gcfg = {
                        lang: 'zh-CN'
                    };
                    (function () {
                        var po = document.createElement('script');
                        po.type = 'text/javascript';
                        po.async = true;
                        po.src = 'https://apis.google.com/js/plusone.js';
                        var s = document.getElementsByTagName('script')[0];
                        s.parentNode.insertBefore(po, s);
                    })();
                </script>
            </h3>

        </div><!-- /.main-bar -->
    </header><!-- /.head -->
</div><!-- /#top -->
<div id="left">
    <div class="media user-media">
        <div class="user-media-toggleHover">
            <span class="fa fa-user"></span>
        </div>

        <div class="user-wrapper">
            <a class="user-link" href="${author.url}">
                <asset:image src="blog/zoowii.jpg" class="media-object img-thumbnail user-img" alt="User Picture"/>
                <span class="label label-danger user-label">Star</span>
            </a>

            <div class="media-body">
                <h5 class="media-heading">${author.userName}</h5>
                <ul class="list-unstyled user-info">
                    <li><a href="${author.url}"><g:message code="site.author"/></a></li>
                    <li>Last Access :
                        <br>
                        <small>
                            <i class="fa fa-calendar"></i>&nbsp;${author.lastLoginTime.format('yyyy-MM-dd')}</small>
                    </li>
                </ul>
            </div>

        </div>
    </div>

    <!-- #menu -->
    <ul id="menu" class="">
        <li class="nav-header"><g:message code="blog.layout.menu"/></li>
        <li class="nav-divider"></li>
        <g:if test="${isAuthor}">
            <li class="">
                <a href="<g:createLink controller="site" action="index"/>">
                    <i class="fa fa-dashboard"></i><span class="link-title">Dashboard</span>
                </a>
            </li>
        </g:if>
        <li>
            <a href="${author?.url}">
                <i class="fa fa-font"></i><span class="link-title">&nbsp;${author?.aliasName}</span>
            </a>
        </li>
        <li class="">
            <a href="javascript:;">
                <i class="fa fa-tasks"></i>
                <span class="link-title">
                    <g:message code="blog.layout.tags"/>
                </span>
                <span class="fa arrow"></span>
            </a>
            <ul>
                <g:each in="${tags}" var="tag" status="counter">
                    <li>
                        <a href="#">
                            <i class="fa fa-angle-right"></i><span class="link-title">&nbsp;${tag}</span>
                        </a>
                    </li>
                </g:each>
            </ul>
        </li>
        <g:if test="${isAuthor}">
            <li>
                <a href="<g:createLink controller="table" action="list"/>">
                    <i class="fa fa-table"></i>
                    <span class="link-title"><g:message code="site.tables"/></span>
                </a>
            </li>
            <li>
                <a href="<g:createLink controller="file" action="list"/>">
                    <i class="fa fa-file"></i>
                    <span class="link-title">
                        <g:message code="site.fileManager"/>
                    </span>
                </a>
            </li>
        </g:if>
        <li>
            <a href="https://github.com/${author?.userName}" target="_blank">
                <i class="fa fa-columns"></i>
                <span class="link-title">
                    Github
                </span>
            </a>
        </li>
        <li class="nav-divider"></li>
        <li>
            <a href="<g:createLink controller="site" action="loginPage"/>">
                <i class="fa fa-sign-in"></i>
                <span class="link-title">
                    <g:message code="site.login"/>
                </span>
            </a>
        </li>
        <li>
            <a href="javascript:;">
                <i class="fa fa-code"></i>
                <span class="link-title">
                    <g:message code="blog.layout.outLinks"/>
                </span>
                <span class="fa arrow"></span>
            </a>
            <ul>
                <li>
                    <g:each in="${outLinks}" var="outLink">
                        <a href="${outLink.url}" title="${outLink.title}">
                            <i class="fa fa-angle-right"></i>
                            <span class="link-titl">
                                &nbsp;
                                <g:if test="${outLink.name}">
                                    ${outLink.name}
                                </g:if>
                                <g:else>
                                    ${outLink.url}
                                </g:else>
                            </span>
                        </a>
                    </g:each>
                </li>
            </ul>
        </li>
        <li class="nav-divider"></li>
        <li class="nav-header"><g:message code="blog.layout.ads"/></li>
        <li class="nav-divider"></li>
        <li>
            <a href="https://www.linode.com/?r=ac463147d488b8d158bddc1ef75ff258bf18c863">
                <i class="fa fa-ticket"></i>
                <span class="link-title">Linode Referral Link</span>
            </a>
        </li>
        <li class="nav-divider"></li>
        <style>

        </style>

    </ul><!-- /#menu -->
</div><!-- /#left -->
<div id="content">
    <div class="outer">
        <div class="inner">
            <div class="col-lg-12">
                <g:layoutBody/>
            </div>
        </div><!-- /.inner -->
    </div><!-- /.outer -->
</div><!-- /#content -->
<div id="right">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>Warning!</strong>  Best check yo self, you're not looking too good.
    </div>

    <!-- .well well-small -->
    <div class="well well-small dark">
        <ul class="list-unstyled">
            <li>Visitor <span class="inlinesparkline pull-right">1,4,4,7,5,9,10</span>
            </li>
            <li>Online Visitor <span class="dynamicsparkline pull-right">Loading..</span>
            </li>
            <li>Popularity <span class="dynamicbar pull-right">Loading..</span>
            </li>
            <li>New Users <span class="inlinebar pull-right">1,3,4,5,3,5</span>
            </li>
        </ul>
    </div><!-- /.well well-small -->

<!-- .well well-small -->
    <div class="well well-small dark">
        <button class="btn btn-block">Default</button>
        <button class="btn btn-primary btn-block">Primary</button>
        <button class="btn btn-info btn-block">Info</button>
        <button class="btn btn-success btn-block">Success</button>
        <button class="btn btn-danger btn-block">Danger</button>
        <button class="btn btn-warning btn-block">Warning</button>
        <button class="btn btn-inverse btn-block">Inverse</button>
        <button class="btn btn-metis-1 btn-block">btn-metis-1</button>
        <button class="btn btn-metis-2 btn-block">btn-metis-2</button>
        <button class="btn btn-metis-3 btn-block">btn-metis-3</button>
        <button class="btn btn-metis-4 btn-block">btn-metis-4</button>
        <button class="btn btn-metis-5 btn-block">btn-metis-5</button>
        <button class="btn btn-metis-6 btn-block">btn-metis-6</button>
    </div><!-- /.well well-small -->

<!-- .well well-small -->
    <div class="well well-small dark">
        <span>Default</span> <span class="pull-right"><small>20%</small></span>

        <div class="progress xs">
            <div class="progress-bar progress-bar-info" style="width: 20%"></div>
        </div>
        <span>Success</span> <span class="pull-right"><small>40%</small></span>

        <div class="progress xs">
            <div class="progress-bar progress-bar-success" style="width: 40%"></div>
        </div>
        <span>warning</span> <span class="pull-right"><small>60%</small></span>

        <div class="progress xs">
            <div class="progress-bar progress-bar-warning" style="width: 60%"></div>
        </div>
        <span>Danger</span> <span class="pull-right"><small>80%</small></span>

        <div class="progress xs">
            <div class="progress-bar progress-bar-danger" style="width: 80%"></div>
        </div>
    </div>
</div><!-- /#right -->
</div><!-- /#wrap -->
<footer id="footer">
    <p>2014 &copy; zoowii. Thanks Metis and Bootstrap's Design</p>
</footer><!-- /#footer -->

<!-- #helpModal -->
<div id="helpModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Modal title</h4>
            </div>

            <div class="modal-body">
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor
                    in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                </p>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal --><!-- /#helpModal -->

<asset:javascript src="jquery-2.1.min.js"/>
<asset:javascript src="bootstrap.min.js"/>
<asset:javascript src="blog/screenfull.js"/>
<asset:javascript src="blog/main.min.js"/>
<asset:javascript src="style-switcher.min.js"/>
</body>
</html>