$(function () {
    AV.initialize(avosAppId, avosAppKey);
    var currentUser = AV.User.current();
    if (!currentUser) {
        window.location.href = loginUrl;
        return;
    }
    $(".username-label").html(currentUser.get('username'));
    $(document).on('click', '.logout-btn', function () {
        AV.User.logOut();
        window.location.href = loginUrl;
    });
});