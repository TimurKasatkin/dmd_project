<#include '../template/template.ftl'>
<#include '../articles_table_macro.ftl'>
<#macro body>
    <@articles_table table_name=journal.name authors=true keywords=true separate_journal_link_details=true/>
</#macro>
<@main title="${journal.name}"/>