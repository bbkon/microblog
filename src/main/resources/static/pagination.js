function fillPaginationList() {
    $.get({
        url: "/unauth/number",
        success: function (number) {
            var $nextButton = $(".next-button").clone();
            $(".next-button").remove();
                var $pageTemplate = $("#page-template");
            for (var i = 0; i < number / 20; i++) {
                    var $pageButton = $pageTemplate.clone();
                    $pageButton.removeAttr("id").removeClass("d-none");
                $pageButton.find(".page-link").text(i + 1);
                $(".pagination").append($pageButton);
                }
            $(".pagination").append($nextButton);
            }
        }
    );
}

fillPaginationList();