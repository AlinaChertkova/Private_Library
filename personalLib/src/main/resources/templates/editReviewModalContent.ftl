<div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span class="close-btn" aria-hidden="true">&times;</span>
          </button>
          <div>
            <h5 class="modal-title" id="exampleModalLabel">${title} <span class="user-name">${review.getUser().login}</span></h5>
          </div>
        </div>
      <form class="review-form">
        <div class="modal-body">
          <span>Оценка:  </span>
            <#list 1..10 as x>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="mark" id="inlineRadio1" value="${x}" required
                      <#if x == review.getMark()>
                        checked
                      </#if>
                  >
                  <label class="form-check-label" for="inlineRadio1">${x}</label>
                </div>
            </#list>
          <textarea class="form-control review-text-area mt-3" name = "text" id="review-text" rows="14"required>${review.getText()}</textarea>
          <input type="hidden" name="bookId" value="${bookId}">
          <input type="hidden" name="id" value="${review.getId()}">
          <input type="hidden" name="type" value="${type}">
        </div>
        <div class="modal-footer">
            <#if type != "user-edit">
                <input type="submit" class="btn delete-btn"  formaction="/review/delete" method="delete" value="Удалить"/>
            </#if>
          <input type="submit" class="btn add-btn" formaction="${path}" method="post"  value="Сохранить" />
        </div>
      </form>
    </div>
  </div>