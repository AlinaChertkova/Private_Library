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

$(document).on('click', '.js-add-btn', function (event){
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

$(document).on('submit', '.review-form', function(e) {
    e.preventDefault();
    var form = $(this);
    var submitter = $(e.originalEvent.submitter);
    var url = submitter.attr('formaction');
    var type = submitter.attr('method');

    $.ajax({
           type: type,
           url: url,
           data: form.serialize(),
           success: function(data)
           {
                $('#review-block').html(data.data);
                $(".modal").modal('hide');
                setAlert(data.message, data.status);
           }
         });
});

$(document).on('submit', '.book-form', function(e) {
    e.preventDefault();
    var form = $(this);
    var url = form.attr('action');
    var type = form.attr('method');

    $.ajax({
           type: type,
           url: url,
           data: form.serialize(),
           success: function(data)
           {
                $(".modal").modal('hide');
                setAlert(data.message, data.status);
           }
         });
});
