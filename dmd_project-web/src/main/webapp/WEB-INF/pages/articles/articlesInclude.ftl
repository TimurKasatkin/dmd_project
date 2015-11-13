<#list articles as article>
<tr>
    <td><a href="${article.url}">${article.title}</a></td>
    <td>
        <#if article.authors??>
            <#list article.authors as author>
                <a href="/authors/${author.id}">
                ${author.firstName} ${author.lastName}
                </a>
                <#if author_has_next>, </#if>
                <br>
            </#list>
        </#if>
    </td>
    <td>${article.year}</td>
    <td>
        <#list article.keywords as keyword>
        <a href="/keywords/${keyword.id}">
        ${keyword.word}
            <#if keyword_has_next>
            </a> , <br>
            </#if>
        </#list>
    </td>
    <td>
        <#if article.journalLink??>
            <#assign jl = article.journalLink>
            <a href="/journals/${jl.journal.id}">
            ${jl.journal.name}</a>,
            <#if jl.volume??>
                <b>vol.</b> ${jl.volume}${jl.number???then(', ','')}
            </#if>
            <#if jl.number??>
                <b>num.</b> ${jl.number}
            </#if>
        </#if>
    </td>
    <td>
        <#if article.conference??>
            <a href="/conferences/${article.conference.id}">${article.conference.name}</a>
        </#if>
    </td>
</tr>
</#list>