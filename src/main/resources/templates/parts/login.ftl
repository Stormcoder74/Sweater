<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">User name:</label>
            <div class="col-sm-4">
                <input class="form-control" type="text" name="username" placeholder="username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-4">
                <input class="form-control" type="password" name="password" placeholder="password"/>
            </div>
        </div>
        <#if isRegisterForm>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email @:</label>
            <div class="col-sm-4">
                <input class="form-control" type="email" name="email" placeholder="some@some.com"/>
            </div>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="col-sm-10">
            <button type="submit" class="btn btn-primary">
                <#if !isRegisterForm>
                    Sign in
                <#else>
                    Create
                </#if>
            </button>
            <#if !isRegisterForm>
                <a class="ml-5" href="/registration">Add new user</a>
            <#else>
                <a class="ml-5" href="/login">Back to login</a>
            </#if>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <button type="submit" class="btn btn-primary">Sign Out</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>