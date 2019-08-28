<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    Login<br>
    <@l.login "/login" />
    <br>
<a href="/registration">Add new user</a>
</@c.page>