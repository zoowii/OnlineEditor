$(function () {
    AV.initialize(avosAppId, avosAppKey);
    var $signupForm = $(".signup-form");
    var isSigningUp = false;
    $signupForm.submit(function (e) {
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
        if (data['email'].length < 5) {
            alert('Email need be at least 5 characters');
            return;
        }
        if (data['password'].length < 5) {
            alert('Password need be at least 5 characters');
            return;
        }
        if (isSigningUp) {
            return;
        }
        isSigningUp = true;
        var user = new AV.User(data);
        user.signUp(null, {
            success: function (user) {
                alert('Sign Up Successfully! Welcome!');
                window.location.href = loginUrl;
                isSigningUp = false;
            },
            error: function (user, error) {
                alert("Error: " + error.code + " " + error.message);
                isSigningUp = false;
            }
        });
    });
});