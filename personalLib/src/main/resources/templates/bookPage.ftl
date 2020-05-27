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
	<link rel="stylesheet" type="text/css" href="/frontend/styles/bookPage.css">
</head>
<body style="background-color: #f7f7f7;">
  <#include "navbar.ftl">

    <div class="container mt-5">
        <div class="row">
            <div class="cover-block">
              <div class="cover-info">
                <img src="/frontend/images/${book.coverLink}" class="cover-book-page" alt="Cover">
              </div>
            </div>
        <div class="discription-block">
          <h6 class="pl-2">${book.avgRating} <i class="fa fa-star" aria-hidden="true"></i></h6>
          <h6 class="pl-2">ISBN: ${book.ISBN}</h6>
          <h2 class="pl-2">
            ${book.title} -
            <#list book.bookAuthors as author>
                ${author.name}
            </#list>
          </h2>
          <div class="discription">
            ${book.description}
          </div>
          <div class="mt-4">
            <input type="button" value="Добавить" class="btn add-btn">
            <input type="button" value="Написать рецензию" class="btn review-btn">

          </div>
          <div class="input-div">

          </div>
        </div>
        <div class="genres-block">
            <#if book.bookGenres??>
                <#list book.bookGenres as genre>
                    <h6>${genre.name}</h6>
                </#list>
            </#if>
        </div>
        </div>

        <div class="row">
            <h4 class="review-title my-4">Рецензии пользователей</h4>
            <div class="review-block">
                <#if reviews??>
                    <#list reviews as review>
                        <#include "reviewCard.ftl">
                    </#list>
                </#if>
            </div>
        </div>
    </div>

  <!--<div class="container">
      <#list books as book>
           <div class="book-cart-cont">
                <#include "bookCard.ftl">
           </div>
      </#list>
  </div>-->
</body>
<script src="frontend/js/jquery/jquery.min.js"></script>
<script src="frontend/bootstrap-4.3.1-dist/js/popper.min.js"></script>
<script src="frontend/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="frontend/js/notification.js"></script>
</html>