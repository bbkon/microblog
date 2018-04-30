$(function () {

    $.get({
        url: "/unauth/entries",
        success: function (response) {
            var $entryTemplate = $("#entry-template");
            for (var i = 0; i < response.length; i++) {
                var entry = response[i];
                var $row = $entryTemplate.clone();
                $row.removeAttr("id")
                    .removeClass("d-none");

            }

            $.each(response, function () {
                var $divrow = "<div class='row'><div class='col-2'>" + this.authorName + "</div><div class='col-8'>" + this.contents + "</div></div>";

                // $divrow
                //     .append("<div class='col-1'>" + this.authorName + "</div>")
                //     .append("<div class='col-11'>" + this.contents + "</div>");
                console.log($divrow);
                $("#entry-template")
                    .append($divrow);

                // $("#entry-author").append("<div>" + this.authorName + "</div>");
                // $("#entry-contents").append("<div>" + this.contents + "</div>");
            });
            // for (var i = 0; i < response.length; i++) {
            //
            //     console.log(response[i].authorName);
            //     console.log(response[i].contents);
            //     $("#entry-author").append(response[i].authorName);
            //     $("#entry-contents").append(response[i].contents);
            //
            //
            // }


        }
    });
});

