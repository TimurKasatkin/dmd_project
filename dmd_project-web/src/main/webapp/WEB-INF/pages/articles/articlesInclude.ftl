<#list articles as article>
<tr>
    <td><a href="${article.url}">${article.title}</a></td>
    <td>
        <#if article.authors??>
            <#list article.authors as author>
            ${author.firstName}, ${author.lastName}<br>
            </#list>
        </#if>

    </td>
    <td>${article.year}</td>
    <td>
        <#if article.journalLink??>
            <#assign jl = article.journalLink>
        ${jl.journal.name}, <b>vol.</b> ${jl.volume}, <b>num.</b> ${jl.number}
        </#if>
    </td>
    <td>
        <#if article.conference??>
                ${article.conference.name}
                </#if>
    </td>
</tr>
</#list>