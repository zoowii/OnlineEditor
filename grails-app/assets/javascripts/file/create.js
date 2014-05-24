$(function () {
    var $newFileForm = $(".new-file-form");
    var BFile = AV.Object.extend('File');
    var user = AV.User.current();
    $newFileForm.submit(function (e) {
        if (!this.checkValidity()) {
            e.preventDefault();
            return;
        }
        e.preventDefault();
        var data = $(this).serializeObject();
        data['is_private'] = data['is_private'] === 'on';
        var fileQuery = new AV.Query(BFile);
        fileQuery.equalTo('author_id', user.id);
        fileQuery.equalTo('name', data['name']);
        fileQuery.limit(1);
        function createFile() {
            var file = new BFile(_.extend({
                author: user.get('username'),
                author_id: user.id,
                content: '',
                mimetype: 'text/plain; charset=UTF-8',
                is_deleted: false,
                version: 1
            }, data));
            file.save(null, {
                success: function (file) {
                    alert('Create file ' + file.get('name') + ' successfully!');
                    window.location.href = '/file/' + file.id + '/edit';
                },
                error: function (error) {
                    alert('Failed to create new file, with error code: ' + error.description);
                }
            });
        }

        fileQuery.find({
            success: function (results) {
                if (results.length > 0) {
                    alert('You have owned a file with the same name, please give another file name');
                    return;
                }
                createFile();
            },
            error: function (error) {
                alert(error.message);
            }
        });
    });
});