<#include "template/template.ftl">
<#macro body>
<div class="content-wrap">
    <div class="container clearfix">
        <form id="fullsearch_form" action="/all">
            <div id="search" class="input-group input-group-lg">
                <input class="form-control required" name="keyWord">
                        <span class="input-group-btn">
                        <button class="btn btn-lg " style="color: rgba(0, 0, 0, 0.71)" type="submit">Search</button>
                    </span>
            </div>
        </form>

    </div>
</div>
</#macro>
<@main customScripts=["/resources/js/index.js"]/>