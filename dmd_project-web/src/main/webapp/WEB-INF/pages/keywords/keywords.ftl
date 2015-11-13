<#include '../template/template.ftl'>
<#macro body>
<div class="content-wrap">
    <div class="container clearfix">
        <center><h2>Keywords</h2></center>
        <#assign col_count=5>
        <div class="row">
            <#assign keywords_size=keywords?size>
            <#assign rows_per_col=(keywords_size/col_count)?ceiling>
            <#list 0..col_count-1 as i>
                <#assign col_class='col_one_fifth'>
                <#if i==col_count-1>
                    <#assign col_class=col_class+' col_last'>
                </#if>
                <ul class="${col_class}">
                    <#list 0..rows_per_col-1 as j>
                        <#assign index=rows_per_col*i+j>
                        <#if index+1 gte keywords_size>
                            <#break>
                        </#if>
                        <#assign keyword=keywords[index]>
                        <li><a href="/keywords/${keyword.id}">${keyword.word}</a></li>
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
                                onclick="window.location.href='${'/keywords?offset='+prevOffset}'">
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
                                onclick="window.location.href='${'/keywords?offset='+nextOffset}'">
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
<@main title="Keywords"/>