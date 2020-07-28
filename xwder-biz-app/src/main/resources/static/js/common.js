// 所有页面通用的js
$(function (){
    // 头像区域获取焦点和失去焦点事件 显示菜单
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

    // 博客分类添加鼠标移入移出显示分类
    $("#showBlogCategory").mouseover(function (){
        $("#showBlogCategoryA").addClass("show");
        $("#showBlogCategoryA").attr("aria-expanded",true);
        $("#showBlogCategoryUl").addClass("show");
    }).mouseout(function (){
        $("#showBlogCategoryA").removeClass("show");
        $("#showBlogCategoryA").attr("aria-expanded",false);
        $("#showBlogCategoryUl").removeClass("show");
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
