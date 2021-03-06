var username;

function checkAuthenticationStatus() {
    $.get({
        url: "/auth/login",
        success: function () {
            greetLoggedInUser();
            showMenuForLoggedInUser();
        },
        error: function () {
            showMenuForNotLoggedIn();
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
            showMenuForLoggedInUser();
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
            window.location.href = "index.html";
            showMenuForNotLoggedIn();
        }
    });
});

function greetLoggedInUser() {
    $.get({
        url: "/auth/service/username",
        success: function (response) {
            username = response;
            $("#user-greeting").text("You are logged in as: " + username);
            return response;
        }
    })
}

function showMenuForLoggedInUser() {
    greetLoggedInUser();
    $("#login-menu-item").hide();
    $("#logout-menu-item").show();
    $("#register-nav-link").hide();
    $("#your-profile-nav-link").show();
}

function showMenuForNotLoggedIn() {
    $("#login-menu-item").show();
    $("#logout-menu-item").hide();
    $("#register-nav-link").show();
    $("#your-profile-nav-link").hide();
}

$(".navbar").on("keypress", "#login-menu-item", function (args) {
    if (args.keyCode == 13) {
        $("#login-button").click();
        return false;
    }
});

checkAuthenticationStatus();