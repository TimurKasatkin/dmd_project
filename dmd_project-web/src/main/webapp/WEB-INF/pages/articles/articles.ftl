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
                        <button class="btn btn-success" data-toggle="modal" data-target="#insertModal">Insert</button>
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

    <div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-body">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="updateModalLabel">Insert new article</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <table cellspacing="10">
                                <tr>
                                    <td ><label class="description" for="titlein">Title:</label></td>
                                    <td><input id="titlein" name="titlein" type="text"></td>
                                </tr>
                                <tr>
                                    <td><label class="description" for="urlin">URL:</label></td>
                                    <td><input id="urlin" name="urlin" type="URL"></td>
                                </tr>
                                <tr>
                                    <td><label class="description" for="yearin">Year:</label></td>
                                    <td><input id="yearin" name="yearin" type="year" pattern="[0-9]{4}"></td>
                                </tr>
                                <tr>
                                    <td><label class="description">Conference or Journal:</label></td>
                                    <td><select id="cjin" onchange="changeJoOrCo()">
                                        <option>Journal</option>
                                        <option>Conference</option>
                                    </select></td>
                                </tr>
                                <tr>
                                    <td ><label class="description" for="journalin">Journal:</label></td>
                                    <td><input id="journalin" name="journalin" type="text"></td>
                                </tr>
                                <tr>
                                    <td ><label class="description" for="conferencein">Conference:</label></td>
                                    <td><input id="conferencein" name="conferencein" type="text" disabled="disabled"></td>
                                </tr>
                                <tr>
                                    <td ><label class="description" for="titlein">Add author:</label></td>
                                    <td><i class="i-plain icon-ok" onclick="addAuthor()"></i></td>
                                </tr>
                            </table>
                            <div id="authorDiv">
                            </div>
                        </form>
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