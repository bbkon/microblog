var username;

function checkAuthenticationStatus() {
    $.get({
        url: "/auth/login",
        success: function () {
            getLoggedInUsername();
            $("#login-menu-item").hide();
        },
        error: function () {
            $("#logout-menu-item").hide();
        }
    });
}

$("#login-button").click(function () {
    var username = $("#username").val();
    var password = $("#password").val();

    $.post({
        url: "/auth/login",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(username + ":" + password));
        },
        success: function () {
            getLoggedInUsername();
            $("#login-menu-item").hide();
            $("#logout-menu-item").show();
        },
        error: function () {
            alert("Invalid credentials!");
        }
    });
});


$("#logout-button").click(function () {
    $.post({
        url: "/auth/login",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(":"));
        },
        success: function () {
            alert("Invalid credentials!");
        },
        error: function () {
            $("#login-menu-item").show();
            $("#logout-menu-item").hide();
        }
    });
});

function getLoggedInUsername() {
    $.get({
        url: "/auth/service/username",
        success: function (response) {
            username = response;
            $("#user-greeting").text("You are logged in as: " + username);
            return response;
        }
    })
}

checkAuthenticationStatus();