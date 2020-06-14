$(document).on('click', '.js-change-password', function (event){
    var block = document.getElementById("changePasswordBlock");
    var fields = '<input type="password" id="password" name="password" class="form-control input-field" placeholder="пароль" required>' +
        '<input type="password" id="rep-password" name="password_rep" class="form-control input-field" placeholder="повторите пароль" required>';
    block.innerHTML = fields;

    var link = $("#changePassword");
    link.removeClass("js-change-password");
    link.addClass("js-cancel-btn");
    link.html('Отмена');
});

$(document).on('click', '.js-cancel-btn', function (event){
    var btn =$(".js-cancel-btn");
    btn.removeClass("js-cancel-btn");
    btn.addClass("js-change-password");
    btn.html('Сменить пароль');

    $("#password").remove();
    $("#rep-password").remove();
});

$(document).on('click', '.js-change-btn', function (event){
    var el = $(this);
	$.ajax({
            method : "POST",
            url : "/book/getModal",
            data: {
                id: el.data('id'),
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
                $('#books-block').html(data.data);
                $(".modal").modal('hide');
                setAlert(data.message, data.status);
           }
         });
});

$(document).on('click', '.js-delete-btn', function(e) {
    var el = $(this);
    $.ajax({
           type: "delete",
           url: "/book/delete",
           data: {
               id: el.data('id')
           },
           success: function(data)
           {
                if (data.status == 'success') {
                    $('#books-block').html(data.data);
                    $(".modal").modal('hide');
                }
                setAlert(data.message, data.status);
           }
         });
});

$(document).on('click', '.js-user-review-edit', function (event){
    var el = $(this);
	$.ajax({
            method : "POST",
            url : "/review/getReview",
            data: {
                bookId: el.data('book'),
                id: el.data('id'),
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

$(document).on('click', '.js-delete-review-btn', function(e) {
    var el = $(this);
    $.ajax({
           type: "delete",
           url: "/user/review/delete",
           data: {
               id: el.data('id')
           },
           success: function(data)
           {
                if (data.status == 'success') {
                    $('#review-block').html(data.data);
                    $(".modal").modal('hide');
                }
                setAlert(data.message, data.status);
           }
         });
});


$(document).on('submit', '.user-info-form', function(e) {
    e.preventDefault();

    var pass = $( "#password" ).val();
    var passRep = $( "#rep-password" )
    var passRepVal = $( "#rep-password" ).val();

    if (pass != passRepVal) {
        passRep.val("");
        passRep.addClass("warning");
        passRep.focus();

        return false;
    }

    var form = $(this);
    var submitter = $(e.originalEvent.submitter);
    var url = submitter.attr('formaction');
    var type = submitter.attr('method');

    $.ajax({
           type: 'put',
           url: "/user/update",
           data: form.serialize(),
           success: function(data)
           {
                if (data.status == 'success') {
                    $('#info-block').html(data.data);
                    $(".modal").modal('hide');
                }

                setAlert(data.message, data.status);
           }
         });
});
