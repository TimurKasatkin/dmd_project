<#include '../template/template.ftl'>
<#macro body>
<div class="content-wrap">

    <div class="container clearfix">
        <center><h2>Authors</h2></center>
        <#list authors as author>
            <#assign f=author.firstName>
            <#assign l=author.lastName>
            <div class="col-md-3">
                <div class="feature-box fbox-center fbox-bg fbox-light fbox-effect">
                    <div class="fbox-icon">
                        <a href="#"><i class="i-alt">${f?substring(0,1)}${l?substring(0,1)}</i></a>
                    </div>
                    <h3>${f} ${l}</h3>
                </div>
            </div>
        </#list>
        <ul class="pagination">
            <li><a href="#">«</a></li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">»</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>
</#macro>
<@main title="Authors"/>