$(function () {
    if (currentPage == 0) {
        getPage(0);
    }
});

$("#submit-entry-button").click(function (xhr) {
    var contents = $("#new-entry-form").val();
    var username = "user";

    var createEntryRequest = {
        contents: contents,
        username: username
    };

    $.post({
        url: "auth/entry",
        data: JSON.stringify(createEntryRequest),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            window.location.href = "index.html"
        }
    });
    return false;
});

