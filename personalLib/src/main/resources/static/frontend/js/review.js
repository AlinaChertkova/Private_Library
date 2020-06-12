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
                    setAlert(result.data, "danger");
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

function setAlert(message, type) {
$("#alerts").append(
    '<div class="alert alert-' + type + ' alert-dismissible" data-delay="800" id="notification" role="alert">' +
      '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
          '<span aria-hidden="true">&times;</span></button>' +
          '<div class="notification-message">' +
              message +
          '</div>' +
    '</div>'
    );
    var notification = $('.alert');
    notification.show();

    $(".alert").delay(1500).slideUp(200, function() {
        $(this).alert('close');
    });
}