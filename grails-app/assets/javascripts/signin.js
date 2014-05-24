$(function () {
    AV.initialize(avosAppId, avosAppKey);
    var $signInForm = $(".form-signin");
    $signInForm.submit(function (e) {
        if (!this.checkValidity()) {
            e.preventDefault();
            return;
        }
        e.preventDefault();
        var data = $(this).serializeObject();
        if (data['username'].length < 5) {
            alert('Username need be at least 5 characters');
            return;
        }
        if (data['password'].length < 5) {
            alert('Password need be at least 5 characters');
            return;
        }
        AV.User.logIn(data['username'], data['password'], {
            success: function (user) {
//                alert('Login successfully! Welcome, ' + user.get('username'));
                window.location.href = '/';
            },
            error: function (user, error) {
                alert("Error: " + error.code + " " + error.message);
            }
        });
    });
});