<g:applyLayout name="main">
    <h4>Edit Article ${article.id}</h4>

    <form action="<g:createLink action="updateMeta"/>" method="POST"
          class="form-horizontal" role="form">
        <input type="hidden" name="id" value="${article.id}"/>

        <div class="form-group">
            <label class="col-sm-2 control-label">Title</label>

            <div class="col-sm-10">
                <input type="text" name="title" class="form-control" placeholder="Title" value="${article.title}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Tags</label>

            <div class="col-sm-10">
                <input type="text" name="tags" value="${article.tags?.join(', ')}" class="form-control"
                       placeholder="Tags">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Date</label>

            <div class="col-sm-10">
                <input type="date" name="date" class="form-control" placeholder="Date"
                       value="${article.date.format('yyyy-MM-dd')}">
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Summary</label>

            <div class="col-sm-10">
                <textarea name="summary" class="form-control" rows="4">${article.summary}</textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Submit</button>
            </div>
        </div>
    </form>
</g:applyLayout>