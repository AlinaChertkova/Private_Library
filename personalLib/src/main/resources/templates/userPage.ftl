<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login Page</title>
   <!--Made with love by Mutiullah Samim -->

	<!--Bootsrap 4 CDN-->
	<!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">-->
	<link rel="stylesheet" type="text/css" href="/frontend/bootstrap-4.3.1-dist/css/bootstrap.min.css">

    <!--Fontawesome CDN-->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

	<!--Custom styles-->
	<link rel="stylesheet" type="text/css" href="/frontend/styles/navbar.css">
	<link rel="stylesheet" type="text/css" href="/frontend/styles/notification.css">
	<link rel="stylesheet" type="text/css" href="/frontend/styles/registration.css">

	<link rel="stylesheet" type="text/css" href="/frontend/styles/tabs.css">
	<link rel="stylesheet" type="text/css" href="/frontend/styles/reviewModal.css">
	<!--<link rel="stylesheet" type="text/css" href="/frontend/styles/bookPage.css">-->
</head>
<body style="background-color: #f7f7f7;">
    <div id="alerts">
    </div>
    <#include "navbar.ftl">
  <div class="container">
    <div class="row">
        <nav>
          <div id="layer1">
            <div>
              <h1>Моя страница</h1>
            </div>
            <div class="nav nav-tabs mt-4" id="nav-tab" role="tablist">
              <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Прочитанное</a>
              <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Рецензии</a>
              <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">Личная информация</a>
            </div>
          </div>
        </nav>
        <div class="tab-content" id="nav-tabContent">
          <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
            <div class="books-block" id="books-block">
                <#include "readBooks.ftl">
            </div>
          </div>
          <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
              <div class="review-block" id="review-block">
                  <#include "userReviews.ftl">
              </div>
          </div>
          <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
            <div id="info-block">
                <#include "userInfo.ftl">
            </div>
          </div>
        </div>
    </div>

    <div class="modal fade float-center" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div id="reviewModalContent"></div>
    </div>
</div>
  <script src="/frontend/js/jquery/jquery.min.js"></script>
  <script src="/frontend/bootstrap-4.3.1-dist/js/popper.min.js"></script>
  <script src="/frontend/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
  <script src="/frontend/js/userPage.js"></script>
  <!--<script src="/frontend/js/registration.js"></script>-->
  <script src="/frontend/js/notification.js"></script>
</body>