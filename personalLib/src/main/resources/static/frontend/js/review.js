//$(document).on('click', '.js-review-read-more', function (event){
//    var $this = $j(this);
//
//
//
//});
//
//
//$(document).ready(function() {
//    // GET REQUEST
//    $("#getALlBooks").click(function(event) {
//        event.preventDefault();
//        ajaxGet(event);
//    });
//
//    // DO GET
//    function ajaxGet() {
//
//
//        $.ajax({
//            type : "GET",
//            url : "/review/",
//            success : function(result) {
//                if (result.status == "success") {
//                    $('#getResultDiv ul').empty();
//                    var custList = "";
//                    $.each(result.data,
//                            function(i, book) {
//                                var user = "Book Name  "
//                                        + book.bookName
//                                        + ", Author  = " + book.author
//                                        + "<br>";
//                                $('#getResultDiv .list-group').append(
//                                        user)
//                            });
//                    console.log("Success: ", result);
//                } else {
//                    $("#getResultDiv").html("<strong>Error</strong>");
//                    console.log("Fail: ", result);
//                }
//            },
//            error : function(e) {
//                $("#getResultDiv").html("<strong>Error</strong>");
//                console.log("ERROR: ", e);
//            }
//        });
//    }
//})
$(document).on('click', '.js-review-read-more', function (event){
    var el = $(this);
    var id = el.data('id');
	$.ajax({
            method : "POST",
            url : "/review/getReview",
            data: {
                id: el.data('id')
            },

            success : function(result) {
                var modal = document.getElementById("reviewModalContent");
                modal.innerHTML = result;
                $("#reviewModal").modal('show');
            },

            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
});