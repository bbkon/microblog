function fillPaginationList() {
    $.get({
            url: "/unauth/entries",
            success: function (entries) {
                var pages = entries.length / 20;
                var $pageTemplate = $("#page-template");
                for (var i = 0; i < pages; i++) {
                    var $pageButton = $pageTemplate.clone();
                    $pageButton.removeAttr("id").removeClass("d-none");
                    $pageButton.text(i);
                    $(".pagination").append($pageTemplate);
                }
            }
        }
    );
}

fillPaginationList();