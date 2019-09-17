<#-- @ftlvariable name="message" type="" -->
<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    Login<br><br>
    ${message!}
    <@l.login "/login" false/><br>
</@c.page>