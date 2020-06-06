<div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span class="close-btn" aria-hidden="true">&times;</span>
        </button>
        <div>
          <h5 class="modal-title" id="exampleModalLabel">Рецензия пользователя <span class="user-name">${review.getUser().login}</span></h5>
        </div>
          <span>Оценка: ${review.getMark()} <i class="fa fa-star" aria-hidden="true"></i></span>
        <span class="time">${review.getPublishingDate()}</span>
      </div>
      <div class="modal-body">
          <p>
            ${review.getText()}
          </p>
      </div>
    </div>
</div>