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
                <th width="25%"><a href="${sortByUrl('title')}">Title</a> ${arrayIfSortedBy('title')}</th>
                <th width="25%">Authors</th>
                <th width="10%"><a href="${sortByUrl('year')}">Year</a> ${arrayIfSortedBy('year')}</th>
                <th width="10%">Keywords</th>
                <th width="10%">Journal</th>
                <th width="10%">Conference</th>
                <th width="10%">Update/Delete</th>
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

    <!-- modal -->
    <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-body">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="updateModalLabel">Modal Heading</h4>
                    </div>
                    <div class="modal-body">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th width="50%"><a href="#">Title</a></th>
                                <th width="25%"><a href="#">URL</a></th>
                                <th width="25%"><a href="#">Year</a></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><input id="modintitle" type="text" value="ada"></input></td>
                                <td><input id="modinurl" type="text" value="hhawjdhja.com"></input></td>
                                <td><input id="modinyear" type="text" value="2300"></input></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- end modal -->

</div>
</#macro>
<@main title="Publications"/>