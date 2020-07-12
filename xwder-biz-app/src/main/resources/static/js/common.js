// 所有页面通用的js

// 头像区域获取焦点和失去焦点事件 显示菜单
$(function (){

    //鼠标的移入移出
    $("#showUserDropdown").mouseover(function (){
        $("#userMenuDropdown").addClass("show");
        $("#userMenuDropdown").attr("aria-expanded",true);
        $("#userMenu").addClass("show");
    }).mouseout(function (){
        $("#userMenuDropdown").removeClass("show");
        $("#userMenuDropdown").attr("aria-expanded",false);
        $("#userMenu").removeClass("show");
    });
});


function login() {
    // 登录
    var currentUrl = window.location.href;
    var ctxPath = /*[[@{/}]]*/'';
    var domain = window.location.protocol+"//"+window.location.host;
    var redirect=domain+ctxPath+"/login.html"+"?redirect="+currentUrl;
    window.location.replace(redirect);
}
function register() {
    // 登录
    var currentUrl = window.location.href;
    var ctxPath = /*[[@{/}]]*/'';
    var domain = window.location.protocol+"//"+window.location.host;
    var redirect=domain+ctxPath+"/register.html"+"?redirect="+domain+ctxPath+"/login.html";
    window.location.replace(redirect);
}
