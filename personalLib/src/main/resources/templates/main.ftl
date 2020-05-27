<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login Page</title>
   <!--Made with love by Mutiullah Samim -->

	<!--Bootsrap 4 CDN-->
	<!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">-->
	<link rel="stylesheet" type="text/css" href="frontend/bootstrap-4.3.1-dist/css/bootstrap.min.css">

    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="frontend/styles/navbar.css">
	<link rel="stylesheet" type="text/css" href="frontend/styles/notification.css">
	<link rel="stylesheet" type="text/css" href="frontend/styles/bookCard.css">
</head>
<body style="background-color: #f7f7f7;">
  <#include "navbar.ftl">

  <div class="container">
      <#list books as book>
           <div class="book-cart-cont">
                <#include "bookCard.ftl">
           </div>
      </#list>
  </div>
</body>
<script src="frontend/js/jquery/jquery.min.js"></script>
<script src="frontend/bootstrap-4.3.1-dist/js/popper.min.js"></script>
<script src="frontend/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="frontend/js/notification.js"></script>
</html>
