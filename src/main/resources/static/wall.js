$(function () {

    $.get({
        url: "/unauth/entries",
        success: function (entries) {
            var $entryTemplate = $("#entry-template");
            for (var i = 0; i < entries.length; i++) {
                var entry = entries[i];
                var $row = $entryTemplate.clone();
                $row.removeAttr("id")
                    .removeClass("d-none");

                $row.find(".entry-author").text(entry.authorName);
                $row.find(".entry-contents").text(entry.contents);

                $(".wall").append($row);

            }

            // $.each(entries, function () {
            //     var $divrow = "<div class='row'><div class='col-2'>" + this.authorName + "</div><div class='col-8'>" + this.contents + "</div></div>";

            // $divrow
            //     .append("<div class='col-1'>" + this.authorName + "</div>")
            //     .append("<div class='col-11'>" + this.contents + "</div>");
            // console.log($divrow);
            // $("#entry-template")
            //     .append($divrow);

            // $("#entry-author").append("<div>" + this.authorName + "</div>");
            // $("#entry-contents").append("<div>" + this.contents + "</div>");
            // });
            // for (var i = 0; i < entries.length; i++) {
            //
            //     console.log(entries[i].authorName);
            //     console.log(entries[i].contents);
            //     $("#entry-author").append(entries[i].authorName);
            //     $("#entry-contents").append(entries[i].contents);
            //
            //
            // }


        }
    });
});

