<#include '../template/template.ftl'>
<#macro body>
<div class="content-wrap">

    <div class="container clearfix">
        <table class="table table-hover">
            <thead>
            <tr>
                <th><a href="#">Title ↓ ↑</a></th>
                <th><a href="#">Authors</a></th>
                <th><a href="#">Year</a></th>
                <th><a href="#">Journal</a></th>
                <th><a href="#">Conference</a></th>
            </tr>
            </thead>
            <tbody>
            <form id="widget-subscribe-form" action="TODO" role="form" method="post"
                  class="nobottommargin col_half" novalidate="novalidate">
                <div class="input-group divcenter">

                    <input class="form-control required email" placeholder="Keywords" aria-required="true"/>
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
<@main title="Publication"/>