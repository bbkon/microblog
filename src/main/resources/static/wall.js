$(function () {
    if (currentPage == 0) {
        getPage(0);
    }
});

$("#submit-entry-button").click(function () {
    var contents = $("#new-entry-form").val();

    var createEntryRequest = {
        contents: contents,
        username: username
    };

    $.post({
        url: "auth/entry",
        data: JSON.stringify(createEntryRequest),
        contentType: "application/json; charset=utf-8",
        success: function () {
            getPage(0);
            $("#new-entry-form").val("");
        }
    });
    return false;
});

