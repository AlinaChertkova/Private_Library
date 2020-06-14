<div class="row mt-4">
    <div class="col ml-4">
        <form path="/user/save" action="put" class="user-info-form">
            <input type="text" id="login" value="${user.getLogin()}" name="username" class="form-control input-field" placeholder="логин" required>
            <input type="text" id="name" name="name" value="${user.getName()}" class="form-control input-field" placeholder="имя" required>
            <div class="change-password-block" id="changePasswordBlock"></div>
            <a type="button" class="js-change-password" id="changePassword">Сменить пароль</a></br>
            <input type="submit" value="Сохранить" class="btn js-save-info-btn save-btn mt-4">
        </form>
    </div>
    <div class="col mr-5">
        <p class="info-text">Дата регистрации: ${user.getRegistrationDate()}</p>
    </div>
</div>



