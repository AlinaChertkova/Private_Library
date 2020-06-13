$(".alert").delay(1500).slideUp(200, function() {
    $(this).alert('close');
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