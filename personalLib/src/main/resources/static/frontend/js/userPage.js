$(document).on('click', '.js-change-password', function (event){
    var block = document.getElementById("changePasswordBlock");
    var fields = '<input type="password" id="password" name="password" class="form-control" placeholder="пароль" required>' +
        '<input type="password" id="rep-password" name="password_rep" class="form-control" placeholder="повторите пароль" required>';
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
