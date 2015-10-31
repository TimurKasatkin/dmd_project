<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<header id="header" class="full-header">

    <div id="header-wrap">

        <div class="container clearfix">

            <div id="primary-menu-trigger"><i class="icon-reorder"></i></div>

            <!-- Logo
            ============================================= -->
            <div id="logo">
                <a href="/" class="standard-logo"><img alt="Innopolis Publications"></a>
                <a href="/" class="retina-logo"><img alt="Innopolis Publications"></a>
            </div>
            <!-- #logo end -->

            <!-- Primary Navigation
            ============================================= -->
            <nav id="primary-menu">

            <@security.authorize access="isAuthenticated()">
                <ul>
                    <li><a href="/articles">
                        <div>Articles</div>
                    </a>

                    </li>

                    <li><a href="/authors">
                        <div>Authors</div>
                    </a>

                    </li>

                    <li class="mega-menu"><a href="/conferences">
                        <div>Conferences</div>
                    </a>

                    </li>

                    <li class="mega-menu"><a href="/journals">
                        <div>Journals</div>
                    </a>

                        <div class="mega-menu-content style-2 col-5 clearfix">

                        </div>
                    </li>

                    <li class="mega-menu"><a href="/keywords">
                        <div>Keywords</div>
                    </a>

                        <div class="mega-menu-content style-2 col-4 clearfix">

                        </div>
                    </li>
                </ul>

                <ul class="js-toplinks">
                    <@security.authentication property="principal.username" var='principal' scope='page'/>
                    <li class="current">
                        <a href="#">
                            <div>${principal}</div>
                        </a>
                        <ul>
                            <li>
                                <a href="#">Profile</a>
                            </li>
                            <li>
                                <a href="#" class="js-exit">Logout
                                    <form id="js-exitForm" action="/logout" method="post">
                                        <button style="display: none"
                                                type="submit">
                                        </button>
                                    </form>
                                </a>
                            </li>
                        </ul>

                    </li>
                </ul>


                <!-- Top Search
            ============================================= -->
                <div id="top-search">
                    <a href="#" id="top-search-trigger"><i class="icon-search3"></i><i class="icon-line-cross"></i></a>

                    <form action="search.html" method="get">
                        <input type="text" name="q" class="form-control" value="" placeholder="Type &amp; Hit Enter..">
                    </form>
                </div>
                <!-- #top-search end -->
            </@security.authorize>

            </nav>
            <!-- #primary-menu end -->

        </div>

    </div>

</header>
