<#include '../template/template.ftl'>
<#macro body>
<div class="content-wrap">
    <div class="container clearfix">
        <div class="content-wrap col-md-8 allmargin-lg">
            <h2>${journal.name}</h2>

        <#--<form id="widget-subscribe-form" action="include/subscribe.php" role="form" method="post"-->
        <#--class="nobottommargin col_half" novalidate="novalidate">-->
        <#--<div class="input-group divcenter">-->

        <#--<input type="email" id="widget-subscribe-form-email" name="widget-subscribe-form-email"-->
        <#--class="form-control required email" placeholder="Keywords" aria-required="true"/>-->
        <#--<span class="input-group-btn">-->
        <#--<button class="btn btn-success" type="submit">Filter</button>-->
        <#--</span>-->
        <#--</div>-->
        <#--</form>-->

            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Title</th>
                    <th>Authors</th>
                    <th>Year</th>
                    <th>Volume</th>
                    <th>Number</th>
                </tr>
                </thead>
                <tbody>
                <#--<#list journal.articleJournals as journalLink>-->
                <#--<tr>-->
                <#--<td>${journalLink.article.title}</td>-->
                <#--<td>-->
                <#--<#list article.authors as author>-->
                <#--${author.firstName}, ${author.lastName}<br>-->
                <#--</#list>-->
                <#--</td>-->
                <#--<td>${journalLink.article.year}</td>-->
                <#--<td>${journalLink.volume}</td>-->
                <#--<td>${journalLink.number}</td>-->
                <#--</tr>-->
                <#--</#list>-->
                    <#list articles as article>
                    <tr>
                        <td>${article.title}</td>
                        <td></td>
                        <td>${article.year}</td>
                        <#assign jl = article.journalLink>
                        <td>${jl.volume}</td>
                        <td>${jl.number}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>


        </div>
    </div>
</div>
</#macro>
<@main title="Journals"/>