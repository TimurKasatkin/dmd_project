<#include '../template/template.ftl'>
<#macro body>
<div class="content-wrap">

    <div class="container clearfix">
        <center><h2>Authors</h2></center>
        <div class="row">
            <#list authors as author>
                <#assign f=author.firstName>
                <#assign l=author.lastName>
                <div id="author" class="col-md-3">
                    <div class="feature-box fbox-center fbox-bg fbox-light fbox-effect">
                        <div class="fbox-icon">
                            <a href="/authors/${author.id}"><i class="i-alt">${f?substring(0,1)}${l?substring(0,1)}</i></a>
                        </div>
                        <h3>${f} ${l}</h3>
                    </div>
                </div>
            </#list>
        </div>
        <div class="clear"></div>
        <div class="row">
            <ul class="pager">
                <li>
                    <#if prevOffset??>
                        <button class="button button-rounded button-reveal button-large button-dark tleft"
                                onclick="window.location.href='${'/authors?offset='+prevOffset}'">
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
                                onclick="window.location.href='${'/authors?offset='+nextOffset}'">
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
<@main title="Authors"/>