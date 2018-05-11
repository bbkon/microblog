// var url_string = window.location.href;
// var url = new URL(url_string);
// var username = url.searchParams.get("user");


function loadProfileData() {
    // console.log(url_string);
    // console.log(url);
    // console.log(username);
    if (username == null) {
        loadSpecificProfile("/auth/profile");
    } else {
        loadSpecificProfile("/auth/" + username + "/profile");
    }
}

function loadSpecificProfile(getUrl) {
    $.get({
        url: getUrl,
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

$(document).ready(function () {
    $("#upload-file-input").on("change", uploadFile);
    loadProfileData();
});

function uploadFile() {
    console.log("WLOZLECH");
    $.ajax({
        url: "/auth/avatar",
        type: "POST",
        data: new FormData($("#upload-file-form")[0]),
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function () {
            // Handle upload success
            $("#upload-file-message").text("File succesfully uploaded");
        },
        error: function () {
            // Handle upload error
            $("#upload-file-message").text(
                "File not uploaded");
        }
    });
} // function uploadFile

