<div class="card mb-4">
    <div>
      <span>${review.getUser().login} написал(а) рецензию</span>
      <span class="time float-right">${review.getPublishingDate()}
      <i class="fas fa-edit" aria-hidden="true"></i>
    </span>
    </div>
    <span>Оценка: ${review.getMark()} <i class="fa fa-star" aria-hidden="true"></i></span>
    <p class="card-text">
        ${review.getText()}
    </p>
    <div>
      <a class="more-link float-right js-review-read-more" data-id="${review.getId()}">Читать далее...</a>
    </div>
</div>
