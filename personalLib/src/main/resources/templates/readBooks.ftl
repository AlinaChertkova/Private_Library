<#if books??>
    <#list books as book>
        <div class="card mt-3 ml-4" style="width: 46%;">
            <div class="row no-gutters">
              <div  style="width: 33%;">
                <img src="/frontend/images/${book.getBook().getCoverLink()}" class="cover-im" alt="image">
              </div>
                <div style="width: 66%;">
                  <div class="card-body">
                    <a href="/book/${book.getBook().getId()}" class="card-title">${book.getBook().getTitle()}</a>
                    <p class="card-text card-book-authors">
                        <small class="text-muted">
                            <#if book.getBook().getBookAuthors()??>
                                <#list book.getBook().getBookAuthors() as author>
                                    ${author.getName()}<#sep>,
                                </#list>
                            </#if>
                        </small>
                    </p>
                    <dev class="marks-block">
                        <span>Моя оценка: ${book.getMark()} <i class="fa fa-star my-mark" aria-hidden="true"></i></span>
                        <span>Средняя оценка: ${book.getBook().getAvgRating()} <i class="fa fa-star" aria-hidden="true"></i></span>
                    </dev>
                    <div class="mt-4">
                        <input type="button" value="Изменить" data-type="delete-book" class="btn add-btn">
                        <input type="button" value="Удалить" data-type="add" class="btn review-btn">
                    </div>
                  </div>
                </div>
            </div>
        </div>
    </#list>
</#if>
