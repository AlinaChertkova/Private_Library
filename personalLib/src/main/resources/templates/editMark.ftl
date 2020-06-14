<div class="modal-dialog modal-dialog-centered" role="document">
  <div class="modal-content">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span class="close-btn" aria-hidden="true">&times;</span>
      </button>
      <div>
        <h5 class="modal-title" id="exampleModalLabel">${title}</h5>
      </div>
    </div>
  <form action="/book/update" method="post" class="book-form">
    <div class="modal-body">
      <span>Оценка:  </span>
        <#list 1..10 as x>
            <div class="form-check form-check-inline">
              <input class="form-check-input" type="radio" name="mark" id="inlineRadio1" value="${x}" required
                <#if book??>
                    <#if x == book.getMark()>
                      checked
                    </#if>
                </#if>
              >
              <label class="form-check-label" for="inlineRadio1">${x}</label>
            </div>
        </#list>
        <input type="hidden" name="id" value="${book.getId()}">
    </div>
    <div class="modal-footer">
      <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
      <input type="submit" class="btn add-btn" value="Сохранить"/>
    </div>
  </form>
</div>
</div>