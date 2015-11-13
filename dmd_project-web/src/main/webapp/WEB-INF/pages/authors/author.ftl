<#include '../template/template.ftl'>
<#include '../articles_table_macro.ftl'>
<#macro body>
    <@articles_table table_name=(author.firstName+' '+author.lastName) keywords=true journals=true conferences=true/>
</#macro>
<@main title="${author.firstName +' '+author.lastName}"/>