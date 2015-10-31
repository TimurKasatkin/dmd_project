<#include "template/template.ftl">
<#macro body>
<div class="content-wrap">

    <div class="container clearfix">
        <div class="tabs divcenter nobottommargin clearfix" id="tab-login-register" style="max-width: 500px;">

            <ul class="tab-nav tab-nav2 center clearfix">
                <li class="inline-block"><a href="#tab-login">Login</a></li>
                <li class="inline-block"><a href="#tab-register">Register</a></li>
            </ul>

            <div class="tab-container">

                <div class="tab-content clearfix" id="tab-login">
                    <div class="panel panel-default nobottommargin">
                        <div class="panel-body" style="padding: 40px;">
                            <form id="login-form" name="login-form" class="nobottommargin" action="/login"
                                  method="post">

                                <h3>Login to your Account</h3>

                                <div class="col_full">
                                    <#if error??>
                                        <label style="color: red" for="login-form-username">${error}</label>
                                        <br/>
                                    </#if>
                                    <label for="login-form-username">Username:</label>
                                    <input type="text" id="login-form-username" name="username"
                                           class="form-control"/>
                                </div>

                                <div class="col_full">
                                    <label for="login-form-password">Password:</label>
                                    <input type="password" id="login-form-password" name="password"
                                           class="form-control"/>
                                </div>

                                <div class="col_full nobottommargin">
                                    <button class="button button-3d button-black nomargin" id="login-form-submit"
                                            name="login-form-submit" value="login">Login
                                    </button>
                                </div>

                            </form>
                        </div>
                    </div>
                </div>

                <div class="tab-content clearfix" id="tab-register">
                    <div class="panel panel-default nobottommargin">
                        <div class="panel-body" style="padding: 40px;">
                            <h3>Register for an Account</h3>

                            <@form.form id="register-form" name="register-form"
                            cssClass="nobottommargin" modelAttribute="user" action="/register" method="post">
                                <div class="col_full">
                                    <label for="register-form-email">Email Address:</label>
                                    <@form.input id="register-form-email" path='email' cssClass="form-control"/>
                                    <label id="email-error-label" for="register-form-email"
                                           style="color: red"><@form.errors path="email"/></label>
                                </div>
                                <div class="col_full">
                                    <label for="register-form-username">Choose a Username:</label>
                                    <@form.input id="register-form-username" path='login' cssClass="form-control"/>
                                    <label id="login-error-label" for="register-form-email"
                                           style="color: red"><@form.errors path="login"/></label>
                                </div>

                                <div class="col_full">
                                    <label for="register-form-password">Choose Password:</label>
                                    <@form.password id="register-form-password" path='password' cssClass='form-control'/>
                                    <label id="password-error-label" for="register-form-email"
                                           style="color: red"><@form.errors path="password"/></label>
                                </div>

                            <#--<div class="col_full">-->
                            <#--<label for="register-form-repassword">Re-enter Password:</label>-->
                            <#--<input type="password" id="register-form-repassword" name="register-form-repassword"-->
                            <#--value=""-->
                            <#--class="form-control"/>-->
                            <#--</div>-->

                                <div class="col_full nobottommargin">
                                    <button class="button button-3d button-black nomargin" id="register-form-submit"
                                            name="register-form-submit"
                                            value="register">Register Now
                                    </button>
                                </div>
                            </@form.form>
                        </div>
                    </div>
                </div>

            </div>

        </div>
    </div>

</div>
</#macro>
<@main
<#--customScripts=["/resources/js/login.js"]-->
/>