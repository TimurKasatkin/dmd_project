<#include 'template/template.ftl'>
<#include 'articles_table_macro.ftl'>
<#macro body>
<div class="content-wrap">
    <center><h2>Search results</h2></center>
    <div class="container clearfix">
        <div class="toggle">
            <div class="togglet toggleta">
                <i class="toggle-closed icon-ok-circle"></i>
                <i class="toggle-open icon-remove-circle"></i>
            ${articles?size!0} articles found
            </div>
            <div class="togglec" style="display: block;">
                <@articles_table has_name=false table_name='' make_wrapper=false/>
            </div>
        </div>

        <div class="toggle">
            <div class="togglet toggleta">
                <i class="toggle-closed icon-ok-circle"></i>
                <i class="toggle-open icon-remove-circle"></i>
            ${authors?size!0} authors found
            </div>
            <div class="togglec" style="display: block;">
                <ul>
                    <#list authors as author>
                        <li><a href="/authors/${author.id}">${author.firstName} ${author.lastName}</a></li>
                    </#list>
                </ul>
            </div>
        </div>

        <div class="toggle">
            <div class="togglet toggleta">
                <i class="toggle-closed icon-ok-circle"></i>
                <i class="toggle-open icon-remove-circle"></i>
            ${journals?size!0} journals found
            </div>
            <div class="togglec" style="display: block;">
                <ul>
                    <#list journals as journal>
                        <li><a href="/journals/${journal.id}">${journal.name}</a></li>
                    </#list>
                </ul>
            </div>
        </div>

        <div class="toggle">
            <div class="togglet toggleta">
                <i class="toggle-closed icon-ok-circle"></i>
                <i class="toggle-open icon-remove-circle"></i>
            ${conferences?size!0} conferences found
            </div>
            <div class="togglec" style="display: block;">
                <ul>
                    <#list conferences as conference>
                        <li><a href="/conferences/${conference.id}">${conference.name}</a></li>
                    </#list>
                </ul>
            </div>
        </div>

        <div class="toggle">
            <div class="togglet toggleta">
                <i class="toggle-closed icon-ok-circle"></i>
                <i class="toggle-open icon-remove-circle"></i>
            ${keywords?size!0} keywords found
            </div>
            <div class="togglec" style="display: block;">
                <ul>
                    <#list keywords as keyword>
                        <li><a href="/keywords/${keyword.id}">${keyword.word}</a></li>
                    </#list>
                </ul>
            </div>
        </div>

        <div class="clear"></div>

    </div>

</div>
</#macro>
<@main/>