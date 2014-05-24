<g:applyLayout name="main">
    <div class="row main-content">
        <div style="text-align: center">
            <h4>Create File</h4>
        </div>

        <form role="form" class="new-file-form" method="POST" action="<g:createLink action="doCreate"/>">
            <div class="form-group">
                <label for="name-field">File Name</label>
                <br/>
                <input type="text" class="form-control" name="name" id="name-field" value="${file.name}"
                       placeholder="Enter File Name"
                       required>
            </div>

            <div class="form-group">
                <label for="description-field">Description(optional)</label>
                <input type="text" class="form-control" name="description" value="${file.description}" multiple="true"
                       id="description-field"
                       placeholder="Description">
            </div>

            <div class="form-group">
                <label>Bucket</label>
                <select name="bucket" class="form-control">
                    <g:each in="${user ? user.buckets : []}" var="bucket">
                        <option value="${bucket.id}">${bucket.name}</option>
                    </g:each>
                </select>
            </div>

            <div class="checkbox">
                <label>
                    <input type="checkbox" name="is_private"/>
                    <span class="strong">Private?</span>
                </label>
            </div>
            <button type="submit" class="btn btn-success">Create File</button>
        </form>
    </div>
</g:applyLayout>