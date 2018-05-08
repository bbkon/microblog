var comments = 0;

function loadProfileData() {
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
}

function fillUpData(user) {
    $(".username").text(user.responseJSON.username);
    $(".user-description").text(user.responseJSON.description);
    var date = new Date(user.responseJSON.creationDate);
    $(".active-since").text("since " + date.toLocaleDateString() + " " + date.toLocaleTimeString());
    getCommentsNumberByAuthor(user.responseJSON.id).done(function (result) {
        $(".comments-added").text("comments added: " + result);
    });
}

function getCommentsNumberByAuthor(userId) {
    return $.get({
        url: "/auth/" + userId + "/commentsNumber",
        success: function (response) {
            comments = response;
        },
        error: function () {
            comments = 0;
        }
    });
}


// var comments = 0;
// $.get({
//     url: "/auth/" + userId + "/commentsNumber",
//     success: function (response) {
//         comments = response;
//     },
//     error: function () {
//         comments = 0;
//     },
//     complete: function () {
//         return comments;
//     }
// });
// }
//
//
// function myAsyncFunction(url) {
//     return new Promise((resolve, reject) = > {
//         const xhr = new XMLHttpRequest();
//     xhr.open("GET", url);
//     xhr.onload = () =
// >
//     resolve(xhr.responseText);
//     xhr.onerror = () =
// >
//     reject(xhr.statusText);
//     xhr.send();
// })
//     ;
// }

loadProfileData();