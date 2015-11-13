<#include '../template/template.ftl'>
<#include '../articles_table_macro.ftl'>
<#macro body>
    <@articles_table table_name=keyword.word authors=true journals=true conferences=true/>
</#macro>
<@main title="${keyword.word}"/>