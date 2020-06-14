<!DOCTYPE html>
<html lang="en">
<head>
	<title>Регистрация</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

	<link rel="stylesheet" type="text/css" href="frontend/styles/registration.css">
	<link rel="stylesheet" type="text/css" href="frontend/styles/login.css">

	<link rel="stylesheet" type="text/css" href="frontend/styles/notification.css">
</head>
<body style="background-color: #666666;">
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
			    <#if message??>
                    <div class="d-flex justify-content-center">
                        <#include "notification.ftl">
                    </div>
                </#if>
				<form action="/registration" method="post" class="login100-form">
					<h3>Регистрация</h3>

					<input type="text" id="login" name="username" class="form-control input-field" placeholder="логин" required>

					<input type="text" id="name" name="name" class="form-control input-field" placeholder="имя" required>

					<input type="password" id="password input-field" name="password" class="form-control" placeholder="пароль" required>

					<input type="password" id="rep-password input-field" name="password_rep" class="form-control" placeholder="повторите пароль" required>

					<div class="float-right flex-column">
						<div class="enter-link">
						    У вас есть аккаунт?<a href="/login">Вход</a>
						</div>
						<div class="form-group">
							<input type="submit" value="Сохранить" id="reg-button" class="btn float-right login_btn">
						</div>
					</div>
				</form>

				<div class="login100-more" style="background-image: url('frontend/images/login_background.jpg');"/>
			</div>

		</div>
	</div>
	<script src="frontend/js/jquery/jquery.min.js"></script>
	<script src="frontend/js/registration.js"></script>
    <script src="frontend/bootstrap-4.3.1-dist/js/popper.min.js"></script>
    <script src="frontend/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
    <script src="frontend/js/notification.js"></script>
</body>
</html>