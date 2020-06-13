<div class="card book-card mt-5">
    <img src="frontend/images/${book.coverLink}" class="card-img-top cover-im" alt="Cover">
    <div class="card-body">
      <h5 class="card-title">${book.title}</h5>
      <p class="card-text">
        <#list book.bookAuthors as author>
            <a class="author">${author.name}</a><br>
        </#list>
      </p>
      <a href="book/${book.id}" class="btn book-btn">Открыть</a>
    </div>
</div>
