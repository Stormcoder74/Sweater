<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form action="/main" method="get" class="form-inline">
                <input type="text" name="filter" class="form-control mr-2" value="${filter!}" placeholder="фильтр"/>
                <button class="btn btn-primary" type="submit">Search</button>
            </form>
        </div>
    </div>
     <button class="btn btn-primary mb-2" type="button" data-toggle="collapse"
             data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
         Add new message
     </button>
<#--<div class="${(textError?? || tagError??)?string('collapse.show', 'collapse')}" id="collapseExample">-->
    <div class="collapse<#if message??>.show</#if>" id="collapseExample">
        <div class="form-group col-md-6">
            <form action="/main" method="post" enctype="multipart/form-data">
                <input type="text" name="text" class="form-control my-2 ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>" placeholder="введите сообщнеие"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
                <input type="text" name="tag" class="form-control my-2 ${(tagError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.tag}</#if>" placeholder="введите тэг"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-primary my-2" type="submit">Send</button>
            </form>
        </div>
    </div>
    <div class="card-columns">
    <#list messages as message>
        <div class="card m-2">
            <div class="card-header">
                ${message.authorName}
            </div>
            <div>
                <#if message.filename??>
                    <img src="\img\${message.filename}" height="120">
                </#if>
            </div>
            <div class="card-body">
                ${message.text}
            </div>
            <div class="card-footer text-muted">
                ${message.tag}
            </div>
        </div>
    <#else>
        No messages
    </#list>
    </div>
</@c.page>