<#macro articles_table table_name make_wrapper=true has_name=true keywords=false authors=false journals=false conferences=false separate_journal_link_details=false>
    <#if make_wrapper>
    <div class="content-wrap">
    <div class="container clearfix">
    <div class="content-wrap col-md-8 allmargin-lg"></#if>
    <#if has_name>
        <h2>${table_name}</h2>
    </#if>

    <table class="table table-hover">
        <thead>
        <tr>
            <th>Title</th>
            <th>Year</th>
            <#if authors>
                <th>Authors</th>
            </#if>
            <#if keywords>
                <th>Keywords</th>
            </#if>
            <#if journals>
                <th>Journal</th>
            </#if>
            <#if conferences>
                <th>Conference</th>
            </#if>
            <#if separate_journal_link_details>
                <th>Volume</th>
                <th>Number</th>
            </#if>
        </tr>
        </thead>
        <tbody>
            <#list articles as article>
            <tr>
                <td><a href="${article.url}">${article.title}</a></td>
                <td>${article.year}</td>
                <#if authors>
                    <td>
                        <#list article.authors as author>
                            <a href="/authors/${author.id}">
                            ${author.firstName} ${author.lastName}
                            </a>${author_has_next?then(', ','')}<br>
                        </#list>
                    </td>
                </#if>
                <#if keywords>
                    <td>
                        <#list article.keywords as keyword>
                        <a href="/keywords/${keyword.id}">
                        ${keyword.word}
                                    ${keyword_has_next?then('</a> , <br>','')}
                                </#list>
                    </td>
                </#if>
                <#if journals>
                    <td>
                        <#if article.journalLink??>
                            <#assign jl = article.journalLink>
                            <a href="/journals/${jl.journal.id}">
                            ${jl.journal.name}</a>
                            <#if !separate_journal_link_details>
                            ${(jl.volume??||jl.number??)?then(', ','')}
                                <#if jl.volume??>
                                    <b>vol.</b> ${jl.volume}${jl.number???then(', ','')}
                                </#if>
                                <#if jl.number??>
                                    <b>num.</b> ${jl.number}
                                </#if>
                            </#if>
                        </#if>
                    </td>
                </#if>
                <#if conferences>
                    <td>
                        <#if article.conference??>
                            <#assign conf=article.conference>
                            <a href="/conferences/${conf.id}">${conf.name}</a>
                        </#if>
                    </td>
                </#if>
                <#if separate_journal_link_details>
                    <#assign jl = article.journalLink>
                    <td>${jl.volume!''}</td>
                    <td>${jl.number!''}</td>
                </#if>
            </tr>
            </#list>
        </tbody>
    </table>
    <#if make_wrapper>
    </div>
    </div>
    </div>
    </#if>
</#macro>