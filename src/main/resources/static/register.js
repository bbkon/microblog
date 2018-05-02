$("#submit-button").click(function () {
    var username = $("#new-username").val();
    var password = $("#new-password").val();
    var email = $("#new-email").val();

    var createPersonRequest = {
        username: username,
        password: password,
        email: email
    };

    $.post({
        url: "/unauth/register",
        data: JSON.stringify(createPersonRequest),
        contentType: "application/json; charset=utf-8",
        success: function () {
            window.location.href = "index.html"
        }
    });
    return false;
});