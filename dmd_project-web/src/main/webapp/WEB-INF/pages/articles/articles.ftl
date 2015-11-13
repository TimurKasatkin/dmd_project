<#include '../template/template.ftl'>
<#function arrayIfSortedBy fieldName>
    <#if sortBy=fieldName>
        <#if isAsc==true><#return '↓'><#else ><#return '↑'></#if>
    <#else>
        <#return ''>
    </#if>
</#function>
<#function sortByUrl fieldName>
    <#return '/articles?sortBy=${fieldName}&isAsc=${(!isAsc)?c}'>
</#function>
<#macro body>
<div class="content-wrap">

    <div class="container clearfix">
        <table class="table table-hover">
            <thead>
            <tr>
                <th><a href="${sortByUrl('title')}">Title</a> ${arrayIfSortedBy('title')}</th>
                <th>Authors</th>
                <th><a href="${sortByUrl('year')}">Year</a> ${arrayIfSortedBy('year')}</th>
                <th>Keywords</th>
                <th>Journal</th>
                <th>Conference</th>
            </tr>
            </thead>
            <tbody>
            <form id="widget-subscribe-form" action="/articles" role="form"
                  class="nobottommargin col_half" novalidate="novalidate">
                <div class="input-group divcenter">

                    <input class="form-control required email" placeholder="Keywords" name="keyword"
                           aria-required="true"/>
                    <span class="input-group-btn">
                        <button class="btn btn-success" type="submit">Filter</button>
                    </span>
                </div>
            </form>
                <#include 'articlesInclude.ftl'>
            </tbody>
        </table>
    </div>

</div>
</#macro>
<@main title="Publications"/>