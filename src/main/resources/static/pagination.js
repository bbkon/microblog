var currentPage = 0;
var totalPages = 0;

function fillPaginationList() {
    $.get({
            url: "/unauth/entries",
            success: function (response) {
                var $pageTemplate = $("#page-template");
                totalPages = response.totalPages;
                for (var i = 0; i < totalPages; i++) {
                    var $pageButton = preparePageButton($pageTemplate, i);
                    $(".pagination").append($pageButton);
                }
                placeNextButton();
            }
        }
    );
}

function preparePageButton($pageTemplate, i) {
    var $pageButton = $pageTemplate.clone();
    $pageButton.removeAttr("id").removeClass("d-none");
    $pageButton.find(".page-link").text(i + 1);
    $pageButton.find(".page-link").attr("id", "page" + i);
    $pageButton.find(".page-link").attr("onclick", "getPage(" + i + ")");
    return $pageButton;
}

function getPage(number) {
    removeDisplayedContent();

    $.get({
        url: "/unauth/entries?page=" + number,
        success: function (response) {
            var $entryTemplate = $("#entry-template");
            currentPage = number;

            $("#pageNumber").html(currentPage + 1);
            for (var i = 0; i < response.content.length; i++) {
                var entry = response.content[i];
                var $row = $entryTemplate.clone();
                $row.removeAttr("id")
                    .removeClass("d-none");
                $row.attr("id", "entry" + entry.id);

                var date = new Date(entry.creationDate);
                $row.find(".entry-date").text(date.toLocaleDateString() + " " + date.toLocaleTimeString());
                $row.find(".entry-author").text(entry.authorName);
                $row.find(".entry-contents").text(entry.contents);
                $row.find(".response-div").find(".form-control").attr("id", "new-comment-form" + entry.id);
                $row.find(".response-div").find("#submit-comment-button").attr("id", "submit-comment-button-" + entry.id);
                $row.find(".response-div").attr("id", "response-div" + entry.id);
                $row.find(".show-response-form").attr("id", entry.id);
                $row.find(".show-response-form").click(function () {
                    var id = $(this).attr("id");
                    $("#response-div" + id).removeClass("d-none");
                    return false;
                });

                var $commentTemplate = $("#comment-template");

                $row.find(".submit-comment-button").click(function () {
                    var id = $(this).attr("id");
                    var res = id.split("-");
                    var idNumber = res[res.length - 1];
                    var path = "/auth/" + idNumber + "/comment";

                    var createCommentRequest = {
                        username: username,
                        contents: $("#new-comment-form" + idNumber).val()
                    };
                    $.post({
                        url: path,
                        data: JSON.stringify(createCommentRequest),
                        contentType: "application/json; charset=utf-8",
                        success: function () {
                            $("#new-comment-form" + idNumber).val("");
                        }
                    });
                    $("#response-div" + id).removeClass("d-none");
                    return false;
                });

                // var $commentTemplate = $("#comment-template");

                for (var j = 0; j < entry.comments.length; j++) {
                    var comment = entry.comments[j];
                    var $commentRow = $commentTemplate.clone();
                    $commentRow.removeAttr("id");
                    if (j < 2) {
                        $commentRow.removeClass("d-none");
                    }
                    if (entry.comments.length > 2) {
                        prepareLoadMoreCommentsButton($row, entry);
                    }
                    var commentDate = new Date(comment.creationDate);
                    $commentRow.find(".comment-date").text(commentDate.toLocaleDateString() + " " + commentDate.toLocaleTimeString());
                    $commentRow.find(".comment-author").text(comment.authorName);
                    $commentRow.find(".comment-contents").text(comment.contents);

                    $row.find(".entry-comment-template").find(".col-12").append($commentRow);
                }
                $(".wall").append($row);
            }
            placePreviousButton();
            placeNextButton();
        }
    });
    return false;
}

function prepareLoadMoreCommentsButton($row, entry) {
    $row.find(".more-comments-link").attr("id", entry.id);
    $row.find(".more-comments-link").click(function () {
        var id = "entry" + $(this).attr("id");
        var petla = $('#' + id).find(".d-none");
        console.log(petla);
        for (var t = 0; t < petla.length; t++) {
            // console.log(petla[t]);
            $(petla[t]).removeClass("d-none");
        }
        return false;
    });
    $(".more-comments-link").removeClass("d-none");
}

function placePreviousButton() {
    var $previousButton = $("#previous-button");
    if (currentPage == 0) {
        $previousButton.removeAttr("onclick");
        $previousButton.addClass("btn disabled");
    } else {
        $previousButton.removeClass("btn disabled");
        $previousButton.attr("onclick", "getPage(" + (currentPage - 1) + ")");
    }
}


function placeNextButton() {
    var $nextButton = $(".next-button").clone();
    $(".next-button").remove();
    if (currentPage >= totalPages - 1) {
        $nextButton.find(".page-link").addClass("btn disabled");
        $nextButton.find(".page-link").removeAttr("onclick");
    } else {
        $nextButton.find(".page-link").removeClass("btn disabled");
        $nextButton.find(".page-link").attr("onclick", "getPage(" + (currentPage + 1) + ")");
    }
    $(".pagination").append($nextButton);
}

function removeDisplayedContent() {
    $(".wall").find(".row").remove();
}

fillPaginationList();