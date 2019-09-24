<#import "parts/common.ftl" as c>

<@c.page>
    <h5>${username}</h5>
    ${message!}
    <form method="post">
        <input type="hidden" name="userame"/>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-4">
                <input class="form-control" type="password" name="password" placeholder="password"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email@:</label>
            <div class="col-sm-4">
                <input class="form-control" type="email" name="email"
                       placeholder="some@some.com" value="${email!''}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="col-sm-10">
            <button type="submit" class="btn btn-primary">Save</button>
            <a class="ml-5" href="/main">To messages</a>
        </div>
    </form>
</@c.page>