<#include "template/template.ftl">
<#macro body>
<div class="content-wrap">
<#--<div class="container clearfix">-->
<#--<center>-->
<#--<div id="search" class="input-group divcenter">-->
<#--<input class="form-control required email" placeholder="Keywords" aria-required="true">-->
<#--<span class="input-group-btn">-->
<#--<button class="btn btn-success" type="submit">Filter</button>-->
<#--</span>-->
<#--</div>-->
<#--<div id="search">-->
<#--<form action="/all" method="get">-->
<#--<input id="field" name="keyWord" type="text"/>-->
<#--<input id="submit" type="submit" value="Search"/>-->
<#--</form>-->
<#--</div>-->
<#--</center>-->
<#--</div>-->
    <div class="container clearfix">
        <form id="fullsearch_form" action="/all">
            <div id="search" class="input-group input-group-lg">
                <input class="form-control required" name="keyWord">
                        <span class="input-group-btn">
                        <button class="btn btn-lg" type="submit">Search</button>
                    </span>
            </div>
        </form>

    </div>
</div>
</#macro>
<@main customScripts=["/resources/js/index.js"]/>