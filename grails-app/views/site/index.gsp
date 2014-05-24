<g:applyLayout name="main">
    <div class="row main-content">
        <div class="col-sm-8 blog-main">
            <h4>Files:</h4>
            <ol class="list-unstyled">
                <g:each in="${files}" var="file" status="counter">
                    <li>
                        <a href="<g:createLink controller="file" action="edit" params="[id: file.id]"/>"
                           title="${file.description}">${file.name}</a>
                    </li>
                </g:each>
                <li>
                    <hr/>
                    <a href="<g:createLink controller="file" action="list"/>">View All</a>
                </li>
            </ol>
        </div>

        <div class="col-sm-3 col-sm-offset-1 blog-sidebar">
            <div class="sidebar-module sidebar-module-inset">
                <h4>About</h4>

                <p>
                    Online Editor is a simple web file editor, and you can use these file otherwhere.
                </p>
            </div>

            <div class="sidebar-module">
                <h4>
                    <a class="btn btn-success btn-sm"
                       href="<g:createLink controller="file" action="create"/>">New File</a>
                </h4>
                <ol class="list-unstyled">
                    <li><a href="<g:createLink controller="file" action="list"/>">Files</a></li>
                    <li>
                        <a href="<g:createLink controller="bucket"/>">Buckets</a>
                    </li>
                    <li>
                        <a href="<g:createLink controller="table" action="list"/>">DataStore</a>
                    </li>
                </ol>
            </div>

            <div class="sidebar-module">
                <h4>Elsewhere</h4>
                <ol class="list-unstyled">
                    <li><a href="https://github.com/zoowii">@zoowii</a></li>
                </ol>
            </div>
        </div>
    </div>
</g:applyLayout>