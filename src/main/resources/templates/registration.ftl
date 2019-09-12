<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    Add new user<br><br>
    ${message!}
    <@l.login "/registration" true/>
</@c.page>