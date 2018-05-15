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
    });
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

            for (var i = 0; i < response.content.length; i++) {
                var entry = response.content[i];
                var $row = $entryTemplate.clone();
                $row.removeAttr("id")
                    .removeClass("d-none");
                $row.attr("id", entry.id);

                var date = new Date(entry.creationDate);
                $row.find(".entry-details").text(date.toLocaleDateString() + " " + date.toLocaleTimeString());
                $row.find(".entry-author").find(".user-profile-link").text(entry.authorName);
                $row.find(".entry-author").find(".user-profile-link").attr("href", "/profile.html?user=" + entry.authorName);
                // var edited = entry.contents.replace(/(^|\s)(#[a-z\d-]+)/ig, "$1<span class='hash_tag'> $2</span>");
                var edited = entry.contents.replace(/(^|\s)(#[a-z\d-]+)/ig, "$1<span class='hash_tag'>&nbsp;$2</span>");

                $row.find(".entry-contents").html(edited);
                $row.find(".entry-votes-number").text(entry.votes);

                var $commentTemplate = $("#comment-template");

                for (var j = 0; j < entry.comments.length; j++) {
                    var comment = entry.comments[j];
                    var $commentRow = $commentTemplate.clone();
                    $commentRow.removeAttr("id");
                    if (j < 2) {
                        $commentRow.removeClass("d-none");
                    }

                    var commentDate = new Date(comment.creationDate);
                    $commentRow.find(".comment-details").text(commentDate.toLocaleDateString() + " " + commentDate.toLocaleTimeString());
                    $commentRow.find(".comment-author").find(".user-profile-link").text(comment.authorName);
                    $commentRow.find(".comment-author").find(".user-profile-link").attr("href", "/profile.html?user=" + comment.authorName);
                    $commentRow.find(".comment-contents").text(comment.contents);

                    $row.find(".entry-comment-template").find(".col-12").append($commentRow);
                }
                $(".wall").append($row);

                prepareLoadMoreCommentsButton(entry.id);
                prepareSubmitCommentButon(entry.id);
                showNewCommentFormLink(entry.id);
                prepareUpvoteEntryButton(entry.id);
            }
            placePreviousButton();
            placeNextButton();
        }
    });
    return false;
}


function showNewCommentFormLink(entryId) {
    var $currentEntry = $("#" + entryId);
    var $showCommentFormLink = $currentEntry.find(".show-response-form");
    $showCommentFormLink.click(function () {
        $currentEntry.find(".response-div").removeClass("d-none");
        return false;
    });

}

$(document).on("click", ".hash_tag", function () {
    var trimmed = $(this).text().substring(2);
    $.get({
        url: "/unauth/entries/tag/" + trimmed,
        success: function (response) {
            console.log(response);


            // CONTINUE WORK FROM THIS PLACE


        }
    })
});

function prepareUpvoteEntryButton(entryId) {
    var $currentEntry = $("#" + entryId);
    var $upvoteEntryButton = $currentEntry.find(".upvote-entry");
    $upvoteEntryButton.click(function () {
        $.get({
            url: "/auth/entry/" + entryId + "/upvote",
            success: function (response) {
                $currentEntry.find(".entry-votes-number").text(response);
            }
        });
        return false;
    })
}

function prepareLoadMoreCommentsButton(entryId) {
    var $currentEntry = $("#" + entryId);
    var $moreCommentsButton = $currentEntry.find(".more-comments-link");
    $moreCommentsButton.removeClass("d-none");
    $moreCommentsButton.click(function () {
        $currentEntry.find(".comment").removeClass("d-none");
    });
    return false;
}

function prepareSubmitCommentButon(entryId) {
    var $currentEntry = $("#" + entryId);
    var $submitCommentButton = $currentEntry.find(".submit-comment-button");

    $submitCommentButton.click(function () {
        var createCommentRequest = {
            username: username,
            contents: $currentEntry.find(".comment-text").val()
        };

        $.post({
            url: "/auth/" + entryId + "/comment",
            data: JSON.stringify(createCommentRequest),
            contentType: "application/json; charset=utf-8",
            success: function (response) {

                var $commentTemplate = $("#comment-template");
                var $commentRow = $commentTemplate.clone();

                $commentRow.removeAttr("id").removeClass("d-none");
                var commentDate = new Date(response.creationDate);
                $commentRow.find(".comment-details").text(commentDate.toLocaleDateString() + " " + commentDate.toLocaleTimeString());
                console.log($commentRow.find(".comment-details"));
                // $commentRow.find(".comment-author").text(response.authorName);
                $commentRow.find(".comment-author").find(".user-profile-link").text(response.authorName);
                $commentRow.find(".comment-author").find(".user-profile-link").attr("href", "/profile.html?user=" + response.authorName);

                $commentRow.find(".comment-contents").text(response.contents);
                $currentEntry.find(".entry-comment-template").find(".col-12").append($commentRow);

                $currentEntry.find(".comment-text").val("");
            }
        });
        return false;
    });
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
// tagReactionToClick();