$(document).on('click', '.js-review-read-more, .fa-edit', function (event){
    var el = $(this);
    var id = el.data('id');
    var card = el.closest('.card');
	$.ajax({
            method : "POST",
            url : "/review/getReview",
            data: {
                id: el.data('id'),
                type: el.data('type'),
                bookId: card.data('book')
            },

            success : function(result) {
                if (result.status == 'success') {
                    var modal = document.getElementById("reviewModalContent");
                    modal.innerHTML = result.data;
                    $("#reviewModal").modal('show');
                } else {
                    setAlert(result.message, result.status);
                }
            },

            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
});

$(document).on('click', '.review-btn', function (event){
    var el = $(this);
    var row = el.closest('.row');
	$.ajax({
            method : "POST",
            url : "/review/getReview",
            data: {
                bookId: row.data('bookid'),
                id: null,
                type: el.data('type')
            },

            success : function(result) {
                if (result.status == 'success') {
                    var modal = document.getElementById("reviewModalContent");
                    modal.innerHTML = result.data;
                    $("#reviewModal").modal('show');
                } else {
                    setAlert(result.message, result.status);
                }
            },

            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
});

$(document).on('click', '.add-btn', function (event){
    var el = $(this);
    var row = el.closest('.row');
	$.ajax({
            method : "POST",
            url : "/review/getReview",
            data: {
                bookId: row.data('bookid'),
                id: null,
                type: el.data('type')
            },

            success : function(result) {
                if (result.status == 'success') {
                    var modal = document.getElementById("reviewModalContent");
                    modal.innerHTML = result.data;
                    $("#reviewModal").modal('show');

                    setAlert(result.message, result.status);
                } else {
                    setAlert(result.message, result.status);
                }
            },

            error : function(e) {
                console.log("ERROR: ", e);
            }
        });
});

