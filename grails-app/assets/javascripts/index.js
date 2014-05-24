$(function () {
    var BFile = AV.Object.extend('File');
    var fileQuery = new AV.Query(BFile);
    var user = AV.User.current();
    fileQuery.equalTo('author', user.get('username'));
    // TODO: paginator
    fileQuery.limit(20);
    fileQuery.skip(0);
    var $fileList = $(".file-list");
    fileQuery.find({
        success: function (files) {
            _.each(files, function (file) {
                var $li = $("<li></li>");
                var $a = $("<a></a>");
                $a.attr('href', '/file/' + file.id + '/edit');
                $a.attr('title', file.get('description'));
                $a.text(file.get('name'));
                $li.append($a);
                $fileList.append($li);
            });
        },
        error: function (error) {
            alert("Error: " + error.code + " " + error.message);
        }
    })
});