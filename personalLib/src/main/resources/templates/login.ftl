<!DOCTYPE html>
<html>
<head>
	<title>Login Page</title>
   <!--Made with love by Mutiullah Samim -->

	<!--Bootsrap 4 CDN-->
	<!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">-->
	<link rel="stylesheet" type="text/css" href="frontend/bootstrap-4.3.1-dist/css/bootstrap.min.css">

    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="frontend/styles/login.css">
	<link rel="stylesheet" type="text/css" href="frontend/styles/notification.css">
</head>
<body>
<div class="container">
    <#if message??>
        <div class="d-flex justify-content-center">
            <#include "notification.ftl">
        </div>
    </#if>
	<div class="d-flex justify-content-center h-100">
		<div class="card">
			<div class="card-header">
				<a href="/"><img class="logo float-left" height="44px" src="frontend/images/logo_light_white.png" alt="Logo"></a>
			</div>
			<div class="card-body">
				<form action="/login" method="post">
					<div class="input-group form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-user"></i></span>
						</div>
						<input type="text" name="username" class="form-control" placeholder="логин" required>

					</div>
					<div class="input-group form-group">
						<div class="input-group-prepend">
							<span class="input-group-text"><i class="fas fa-key"></i></span>
						</div>
						<input type="password" name="password" class="form-control" placeholder="пароль" required>
					</div>
					<div class="form-group">
						<input type="submit" value="Войти" class="btn float-right login_btn">
					</div>
				</form>
			</div>
			<div class="card-footer">
				<div class="d-flex justify-content-center links">
					У вас есть аккаунт?<a href="/registration">Регистрация</a>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script src="frontend/js/jquery/jquery.min.js"></script>
<script src="frontend/bootstrap-4.3.1-dist/js/popper.min.js"></script>
<script src="frontend/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="frontend/js/notification.js"></script>
</html>