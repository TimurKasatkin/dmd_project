<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#--TODO registration form-->

<@form.form id="register-form" name="register-form"
cssClass="nobottommargin" modelAttribute="user" action="/register" method="post">
<div class="col_full">
    <label for="register-form-email">Email Address:</label>
    <@form.input id="register-form-email" path='email' cssClass="form-control"/>
    <@form.errors path="email"/>
</div>
<div class="col_full">
    <label for="register-form-username">Choose a Username:</label>
    <input type="text" id="register-form-username" name="register-form-username" value="" class="form-control"/>
</div>

<div class="col_full">
    <label for="register-form-password">Choose Password:</label>
    <input type="password" id="register-form-password" name="register-form-password" value="" class="form-control"/>
</div>

<div class="col_full">
    <label for="register-form-repassword">Re-enter Password:</label>
    <input type="password" id="register-form-repassword" name="register-form-repassword" value=""
           class="form-control"/>
</div>

<div class="col_full nobottommargin">
    <button class="button button-3d button-black nomargin" id="register-form-submit" name="register-form-submit"
            value="register">Register Now
    </button>
</div>
</@form.form>