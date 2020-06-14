<#if reviews??>
    <#list reviews as review>
        <div class="card card-user-review mt-3 ml-4 pr-4" style=" width: 100%;">
            <div class="row no-gutters">
              <div  style="width: 20%;">
              <div class="mt-4 ml-5">
                <img src="/frontend/images/${review.getBook().getCoverLink()}" class="cover-im" alt="image">
                <dev class="mt-4">
                    <input type="button" value="Изменить"
                        class="btn js-user-review-edit change-btn ml-4 mt-4"
                        data-book="${review.getBook().getId()}"
                        data-type="user-edit"
                        data-id="${review.getId()}"
                    >
                    <input type="button" value="Удалить"
                        data-id="${review.getId()}"
                        class="btn js-delete-review-btn delete-btn ml-4 mt-2"
                    >
                </dev>
               </div>
              </div>
                <div style="width: 80%;">
                  <div class="card-body">
                    Рецензия на книгу: <a href="/book/${review.getBook().getId()}" class="card-title">${review.getBook().getTitle()}</a>
                    <p class="card-text card-book-authors">
                        <small class="text-muted">
                            <#if review.getBook().getBookAuthors()??>
                                <#list review.getBook().getBookAuthors() as author>
                                    ${author.getName()}<#sep>,
                                </#list>
                            </#if>
                        </small>
                    </p>
                    <dev class="marks-block">
                        <span>Моя оценка: ${review.getMark()} <i class="fa fa-star my-mark" aria-hidden="true"></i></span><br/>
                    </dev>
                    <p class="card-text">
                        ${review.getText()}
                    </p>
                  </div>
                </div>
            </div>
        </div>
    </#list>
</#if>