<#if books??>
    <#list books as book>
        <div class="card mt-3 ml-4" style=" width: 45%;">
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
                        <span>Моя оценка: ${book.getMark()} <i class="fa fa-star my-mark" aria-hidden="true"></i></span><br/>
                        <span>Средняя оценка: ${book.getBook().getAvgRating()} <i class="fa fa-star" aria-hidden="true"></i></span>
                    </dev>
                    <div class="mt-4">
                        <input type="button" value="Изменить"
                            data-id="${book.getId()}"
                            data-type="mark"
                            class="btn change-btn js-change-btn"
                        >
                        <input type="button" value="Удалить"
                            data-id="${book.getId()}"
                            data-type="delete-book"
                            class="btn delete-btn js-delete-btn"
                        >
                    </div>
                  </div>
                </div>
            </div>
        </div>
    </#list>
</#if>
