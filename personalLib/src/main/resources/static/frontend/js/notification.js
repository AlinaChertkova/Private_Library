//$(document).ready(function() {
//    if (!$('toast-body').is(':empty')) {
//        $('#error-notification').toast('show');
//    }
//});

$(".alert").delay(1500).slideUp(200, function() {
    $(this).alert('close');
});