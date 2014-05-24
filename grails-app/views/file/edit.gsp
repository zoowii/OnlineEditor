<g:applyLayout name="main">
    <asset:javascript src="src-min/ace.js"/>
    <asset:javascript src="src-min/mode-css.js"/>
    <asset:javascript src="src-min/mode-html.js"/>
    <asset:javascript src="src-min/mode-java.js"/>
    <asset:javascript src="src-min/mode-javascript.js"/>
    <asset:javascript src="src-min/mode-json.js"/>
    <asset:javascript src="src-min/mode-lisp.js"/>
    <asset:javascript src="edit_file.js"/>
    <script>
        var isNew = "${isNew}" === 'true';
        var fileId = "${file.id}";
        var fileVersion = parseInt("${file.fileVersion}");
        var updateFileUrl = "<g:createLink action="createOrUpdate"/>";
        var changeFileAclurl = "<g:createLink action="changeAcl"/>";
        var mimeType = "${file.mimeType}";
        var isPrivate = "${file.isPrivate}" === 'true';
    </script>

    <div class="row main-content">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 title="${file.description}">
                    <g:if test="${file.isPrivate}">
                        <span class="glyphicon glyphicon-lock"></span>
                    </g:if>
                    <g:if test="${isNew}">
                        Create File
                    </g:if>
                    <g:if test="${!isNew}">
                        File: ${file.name}
                    </g:if>
                </h4>
                <span>MIME Type:</span>
                <select class="mimetype-select">
                    <option value="text/plain; charset=UTF-8">text/plain</option>
                    <option value="text/css; charset=utf-8">Css</option>
                    <option value="appliation/javascript">JavaScript</option>
                    <option value="text/html; charset=UTF-8">HTML</option>
                </select>
                <button class="btn btn-primary btn-sm save-btn">Save</button>
                <button class="btn change-acl-btn btn-default btn-sm">
                    <g:if test="${file.isPrivate}">
                        Publish
                    </g:if>
                    <g:if test="${!file.isPrivate}">
                        UnPublish
                    </g:if>
                </button>
                <a class="btn btn-info btn-sm"
                   href="<g:createLink action="get" params="[id: file.id]"/>"
                   target="_blank">View File</a>
                <a href="<g:createLink action="getMarkDown" params="[id: file.id]"/>"
                   class="btn btn-primary btn-sm" target="_blank">as MarkDown</a>
                <a href="#" class="btn btn-success btn-sm">MarkDown Preview</a>

                <div class="success-msg inline-alert alert alert-success" style="display: none">
                    Well done! You successfully read this important alert message.
                </div>
            </div>

            <div class="panel-body">
                <div id="file-editor">${file.content}</div>
            </div>
        </div>
    </div>
</g:applyLayout>