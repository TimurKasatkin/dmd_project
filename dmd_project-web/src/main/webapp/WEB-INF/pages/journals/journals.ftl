<#include '../template/template.ftl'>
<#macro body>
<div class="content-wrap">
    <div class="container clearfix">
        <center><h2>Journal</h2></center>

        <div class="row">
            <#assign rows_per_col=(journals?size/4)?ceiling>
            <#assign journals_size=journals?size>
            <#list 0..3 as i>
                <#assign col_class='col_one_fourth'>
                <#if i==3>
                    <#assign col_class=col_class+' col_last'>
                </#if>
                <ul class="${col_class}">
                    <#list 0..rows_per_col-1 as j>
                        <#assign index=rows_per_col*i+j>
                        <#if index+1 gte journals_size>
                            <#break>
                        </#if>
                        <#assign journal=journals[index]>
                        <li><a href="/journals/${journal.id}">${journal.name}</a></li>
                    </#list>
                </ul>
            </#list>
        </div>
        <div class="clear"></div>
        <div class="row">
            <ul class="pager">
                <li>
                    <#if prevOffset??>
                        <button class="button button-rounded button-reveal button-large button-dark tleft"
                                onclick="window.location.href='${'/journals?offset='+prevOffset}'">
                            <span>Previous</span>
                            <i class="icon-angle-left"></i>
                        </button>
                    <#else>
                        <button class="button button-rounded button-large button-light disabled">Previous</button>
                    </#if>
                </li>
                <li>
                    <#if nextOffset??>
                        <button class="button button-rounded button-reveal button-large button-dark tright"
                                onclick="window.location.href='${'/journals?offset='+nextOffset}'">
                            <i class="icon-angle-right"></i>
                            <span>Next</span>
                        </button>
                    <#else>
                        <button class="button button-rounded button-large button-light disabled">Next</button>
                    </#if>
                </li>
            </ul>
        </div>


    </div>

</div>
</#macro>
<@main title="Journals"/>