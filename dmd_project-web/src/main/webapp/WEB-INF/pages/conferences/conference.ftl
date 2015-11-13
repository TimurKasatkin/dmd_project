<#include '../template/template.ftl'>
<#include '../articles_table_macro.ftl'>
<#macro body>
    <@articles_table table_name=conference.name authors=true keywords=true/>
</#macro>
<@main title="${conference.name}"/>