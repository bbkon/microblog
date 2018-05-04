var currentPage = 0;
var totalPages = 0;

function fillPaginationList() {
    $.get({
            url: "/unauth/entries",
            success: function (response) {
                var $pageTemplate = $("#page-template");
                totalPages = response.totalPages;
                for (var i = 0; i < totalPages; i++) {
                    var $pageButton = $pageTemplate.clone();
                    $pageButton.removeAttr("id").removeClass("d-none");
                    $pageButton.find(".page-link").text(i + 1);
                    $pageButton.find(".page-link").attr("id", "page" + i);
                    $pageButton.find(".page-link").attr("onclick", "getPage(" + i + ")");
                    $(".pagination").append($pageButton);
                }

                placeNextButton();
            }
        }
    );
}

function getPage(number) {
    removeDisplayedContent();

    $.get({
        url: "/unauth/entries?page=" + number,
        success: function (response) {
            console.log(response);
            var $entryTemplate = $("#entry-template");
            currentPage = number;

            $("#pageNumber").html(currentPage + 1);
            for (var i = 0; i < response.content.length; i++) {
                var entry = response.content[i];
                var $row = $entryTemplate.clone();
                $row.removeAttr("id")
                    .removeClass("d-none");
                var date = new Date(entry.creationDate);
                $row.find(".entry-date").text(date.toLocaleDateString() + " " + date.toLocaleTimeString());
                $row.find(".entry-author").text(entry.authorName);
                $row.find(".entry-contents").text(entry.contents);

                console.log(date.toLocaleTimeString());   // -> 02/28/2004


                $(".wall").append($row);
            }
            placePreviousButton();
            placeNextButton();
        }
    });
    return false;
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

fillPaginationList()