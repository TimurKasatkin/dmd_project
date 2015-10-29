<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>
<#macro main title="Innopolis Publications" customStyles=[] customScripts=[]>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <title>${title}</title>
    <link href="/resources/images/logo.png" rel="icon">
<#--<link href="http://fonts.googleapis.com/css?family=Lato:300,400,400italic,600,700|Raleway:300,400,500,600,700|Crete+Round:400italic"-->
<#--rel="stylesheet" type="text/css"/>-->
    <link href="/resources/css/canvas/bootstrap.css" rel="stylesheet">
    <link href="/resources/css/canvas/style.css" rel="stylesheet">
    <link href="/resources/css/canvas/dark.css" rel="stylesheet">
    <link href="/resources/css/canvas/font-icons.css" rel="stylesheet">
    <link href="/resources/css/canvas/animate.css" rel="stylesheet">
    <link href="/resources/css/canvas/magnific-popup.css" rel="stylesheet">
    <link href="/resources/css/canvas/responsive.css" rel="stylesheet">
    <#list customStyles as style>
        <link rel="stylesheet" href="${style}">
    </#list>
    <!--[if lt IE 9]>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
    <![endif]-->
    <script src="/resources/js/canvas/jquery.js"></script>
    <script src="/resources/js/canvas/plugins.js"></script>
    <#list customScripts as script>
        <script src="${script}"></script>
    </#list>
</head>
<body class="stretched">
<!-- Document Wrapper
============================================= -->
<div id="wrapper" class="clearfix">
    <#include "header.ftl">
    <!-- Content
    ============================================= -->
    <section id="content">

        <div class="content-wrap">

            <div class="container clearfix">
                <@body/>
            </div>

        </div>

    </section>
    <!-- #content end -->

    <#include "footer.ftl">

</div>
<!-- #wrapper end -->

<!-- Go To Top
    ============================================= -->
<div id="gotoTop" class="icon-angle-up"></div>

<!-- Footer Scripts
============================================= -->
<script type="text/javascript" src="/resources/js/canvas/functions.js"></script>

</body>
</html>
</#macro>