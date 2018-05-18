$("#submit-button").click(function () {
    var username = $("#new_username").val();
    var password = $("#new_password").val();
    var email = $("#new_email").val();

    var createPersonRequest = {
        username: username,
        password: password,
        email: email
    };

    if ($("#registration-form").valid()) {
        $.post({
            url: "/unauth/register",
            data: JSON.stringify(createPersonRequest),
            contentType: "application/json; charset=utf-8",
            success: function () {
                window.location.href = "index.html"
            }
        });
        return false;
    }
});

$(function () {
    $("#registration-form").validate({
        rules: {
            new_username: {
                required: true,
                minlength: 3,
                maxlength: 20
            },
            new_password: {
                required: true,
                minlength: 4,
                maxlength: 100
            },
            confirm_new_password: {
                required: true,
                minlength: 4,
                maxlength: 100,
                equalTo: "#new_password"
            },
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            new_username: {
                required: "Please enter your username",
                minlength: "Your username must be of at least 3 characters",
                maxlength: "Your username cannot be of more than 20 characters",
            },
            new_password: {
                required: "Please enter your password",
                minlength: "Your password must be of at least 4 characters",
                maxlength: "Your password cannot be of more than 100 characters"
            },
            confirm_new_password: {
                required: "Please enter your password",
                minlength: "Your password must be of at least 4 characters",
                maxlength: "Your password cannot be of more than 100 characters",
                equalTo: "Please provide the same password as above"
            },
            email: {
                required: "Please enter your e-mail address",
                email: "This is not a correct e-mail address"
            }
        }
    })
});


select.error, textarea.error, input.error
{
    color:#
    FF0000;
}
