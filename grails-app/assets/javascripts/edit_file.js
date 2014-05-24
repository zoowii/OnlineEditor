$(function () {
    var editor = ace.edit('file-editor');
//    editor.setTheme('ace/theme/textmate');
//    editor.setTheme('ace/mode/css');
    var $successMsgEl = $(".success-msg");

    $(".mimetype-select").val(mimeType);

    function showMsg(msg) {
        $successMsgEl.html(msg);
        $successMsgEl.show();
        setTimeout(function () {
            $successMsgEl.hide();
        }, 2500);
    }

    $('.save-btn').click(function () {
        var content = editor.getValue();
        var mimeType = $(".mimetype-select").val();
        console.log(content);
        $.post(updateFileUrl, {
            id: fileId,
            version: fileVersion,
            content: content,
            mime_type: mimeType
        }, function (json) {
            if (json.success) {
                showMsg("Saved Successfully!");
                if (window.isNew) {
                    window.location.reload();
                }
                window.fileVersion = json.data.version;
            } else {
                showMsg(json.data);
            }
        }, 'json');
    });
    $(".change-acl-btn").click(function () {
        $.post(changeFileAclurl, {
            version: fileVersion
        }, function (json) {
            if (json.success) {
                showMsg('Change ACL successfully!');
                setTimeout(function () {
                    window.location.reload();
                }, 1000);
                window.fileVersion = json.data.version;
            } else {
                showMsg(json.data);
            }
        }, 'json');
    });
});