<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div>
        <@l.logout />
        <a href="/user">User list</a>
    </div><br><br>
    <div>
        <form action="/main" method="post" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="введите сообщнеие"/>
            <input type="text" name="tag" placeholder="введите тэг"/>
            <input type="file" name="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit">Отправить</button>
        </form>
    </div><br>
    <div>Список сообщений</div>
    <div>
        <form action="/main" method="get">
            <input type="text" name="filter" value="${filter?ifExists}" placeholder="фильтр"/>
            <button type="submit">Найти</button>
        </form>
    </div><br>
    <#list messages as message>
        <div>
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>
            <div>
                <#if message.filename??>
                    <img src="\img\${message.filename}" height = "120">
                </#if>
            </div>
        </div>
    <#else>
        No messages
    </#list>
</@c.page>