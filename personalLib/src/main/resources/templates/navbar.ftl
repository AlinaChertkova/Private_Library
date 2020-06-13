<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>
<nav class="navbar navbar-expand">
    <div class="container">
          <a class="navbar-brand" href="/">
            <img src="/frontend/images/logo.png" height="50" alt="">
          </a>

          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

        <form class="search-form ml-2" action="/search" method="get">
            <div class="input-group">
                <input type="text" name="searchParam" class="form-control form-control-search" placeholder="Поиск">
                <div class="input-group-append">
                  <button class="btn btn-search" type="submit">
                    <i class="fa fa-search"></i>
                  </button>
                </div>
            </div>
        </form>

        <div class="collapse navbar-collapse">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link" href="/">Каталог</a>
            </li>
            <#if known>
                <li class="nav-item ml-4">
                  <a class="nav-link" href="/mypage">Моя страница</a>
                </li>
            </#if>
            <#if known>
                 <li class="nav-item ml-4">
                  <a class="nav-link" href="/logout">Выход</a>
                </li>
             <#else>
                <li class="nav-item ml-4">
                  <a class="nav-link" href="/login">Вход</a>
                </li>
            </#if>
          </ul>
        </div>
    </div>
</nav>