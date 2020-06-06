<!--<div aria-live="polite" aria-atomic="true" class="d-flex justify-content-center">
    <div class="toast" data-delay="800" id="error-notification" role="alert" aria-live="assertive" aria-atomic="true">
      <div class="toast-header">
        <button type="button" class="float-right mr-2 mb-1 close" data-dismiss="toast" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="toast-body">
       {{#message}}
            {{message}}
       {{/message}}
      </div>
    </div>
</div>

<div class="d-flex justify-content-center">
    <div class="alert alert-success alert-dismissible" data-delay="800" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span></button>
            <div>
                {{message}}
            </div>
    </div>
</div>-->

<div class="alert alert-success alert-dismissible" data-delay="800" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span></button>
        <div>
            ${message}
        </div>
</div>