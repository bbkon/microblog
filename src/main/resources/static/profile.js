var url_string = window.location.href;
var url = new URL(url_string);
var username = url.searchParams.get("user");


function loadProfileData() {
    console.log(url_string);
    console.log(url);
    console.log(username);
    if (username == null) {
        $.get({
            url: "/auth/profile",
            success: function (user) {
            },
            error: function () {
                console.log("Failed to get user data.");
            },
            complete: function (user) {
                fillUpData(user);
            }
        });
    } else {
        $.get({
            url: "/auth/" + username + "/profile",
            success: function (user) {
            },
            error: function () {
                console.log("Failed to get user data.");
            },
            complete: function (user) {
                fillUpData(user);
            }
        });
    }

}

function fillUpData(user) {
    $(".username").text(user.responseJSON.username);
    $(".user-description").text(user.responseJSON.description);
    var date = new Date(user.responseJSON.creationDate);
    $(".active-since").text("since " + date.toLocaleDateString() + " " + date.toLocaleTimeString());
    getCommentsNumberByAuthor(user.responseJSON.username).done(function (result) {
        $(".comments-added").text("comments added: " + result);
    });
    getEntriesNumberByAuthor(user.responseJSON.username).done(function (result) {
        $(".entries-created").text("entries created: " + result);
    })
}

function getCommentsNumberByAuthor(username) {
    return $.get({
        url: "/auth/" + username + "/commentsNumber",
        success: function (response) {
        },
        error: function () {
        }
    });
}

function getEntriesNumberByAuthor(username) {
    return $.get({
        url: "/auth/" + username + "/entriesNumber",
        success: function () {
        },
        error: function () {
        }
    });
}

loadProfileData();