let url = window.location.pathname;
let ctxPath = /*[[@{/}]]*/'';
console.log(url);
console.log(ctxPath);

let blogId = $("#blogId").val();
let blogUserId = $("#blogUserId").val();

$(function () {
    $('.content').flexText();
});


function keyUP(t) {
    let len = $(t).val().length;
    if (len > 139) {
        $(t).val($(t).val().substring(0, 140));
    }
}


$('.commentAll').on('click', '.plBtn', function () {
    let myDate = new Date();
    //获取当前年
    let year = myDate.getFullYear();
    //获取当前月
    let month = myDate.getMonth() + 1;
    //获取当前日
    let date = myDate.getDate();
    let h = myDate.getHours();       //获取当前小时数(0-23)
    let m = myDate.getMinutes();     //获取当前分钟数(0-59)
    if (m < 10) m = '0' + m;
    let s = myDate.getSeconds();
    if (s < 10) s = '0' + s;
    let now = year + '-' + month + "-" + date + " " + h + ':' + m + ":" + s;
    //获取输入内容
    let oSize = $(this).siblings('.flex-text-wrap').find('.comment-input').val();
    console.log(oSize);


    let commentData = {}
    commentData.subjectId = blogId;
    commentData.commentUrl = url;
    commentData.content = oSize;
    commentData.ownerId = blogUserId;
    // 提交前登录检测
    // 评论内容不能weinull
    if (commentData.content == "" || commentData.content.length < 2) {
        alert("评论内容不能未空或者少于两个字符");
        return;
    }
    // ajax 提交评论
    $.ajax({
        type: "post",
        url: "/comment/blog/commit",
        data: commentData,
        success: function (data) {
            console.log(data);
            if (data.code == 200) {
                //动态创建评论模块
                oHtml = '<div class="comment-show-con clearfix"><div class="comment-show-con-img pull-left"><img  src="'+data.data.fromAvatar+'" alt=""></div> ' +
                    '<div class="comment-show-con-list pull-left clearfix"><div class="pl-text clearfix"> <a href="#" class="comment-size-name">'+data.data.fromName+' : </a> <span class="my-pl-con">&nbsp;' + oSize + '</span> </div> <div class="date-dz"> <span class="date-dz-left pull-left comment-time">' + data.data.commentTime + '</span> ' +
                    '<div class="date-dz-right pull-right comment-pl-block"><a href="javascript:;" class="removeBlock">删除</a> <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> ' +
                    '<span class="pull-left date-dz-line">|</span> <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">'+data.data.likeNum+'</i>)</a> </div> </div><div class="hf-list-con"></div></div> </div>';
                if (oSize.replace(/(^\s*)|(\s*$)/g, "") != '') {
                    $('.plBtn').parents('.reviewArea ').siblings('.comment-show').prepend(oHtml);
                    $('.plBtn').siblings('.flex-text-wrap').find('.comment-input').prop('value', '').siblings('pre').find('span').text('');
                }
                alert("发表评论成功");
            } else {
                alert("发表评论失败，请稍后再试！" + data.message)
            }
        },
        error: function (data) {
            alert("发表评论失败，请稍后再试！")
        }
    });
});


$('.comment-show').on('click', '.pl-hf', function () {
    //获取回复人的名字
    let fhName = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
    //回复@
    let fhN = '回复@' + fhName;
    //let oInput = $(this).parents('.date-dz-right').parents('.date-dz').siblings('.hf-con');
    let fhHtml = '<div class="hf-con pull-left"> <textarea class="content comment-input hf-input" placeholder="" onkeyup="keyUP(this)"></textarea> <a href="javascript:;" class="hf-pl">评论</a></div>';
    //显示回复
    if ($(this).is('.hf-con-block')) {
        $(this).parents('.date-dz-right').parents('.date-dz').append(fhHtml);
        $(this).removeClass('hf-con-block');
        $('.content').flexText();
        $(this).parents('.date-dz-right').siblings('.hf-con').find('.pre').css('padding', '6px 15px');
        //console.log($(this).parents('.date-dz-right').siblings('.hf-con').find('.pre'))
        //input框自动聚焦
        $(this).parents('.date-dz-right').siblings('.hf-con').find('.hf-input').val('').focus().val(fhN);
    } else {
        $(this).addClass('hf-con-block');
        $(this).parents('.date-dz-right').siblings('.hf-con').remove();
    }
});


$('.comment-show').on('click', '.hf-pl', function () {
    let oThis = $(this);
    let myDate = new Date();
    //获取当前年
    let year = myDate.getFullYear();
    //获取当前月
    let month = myDate.getMonth() + 1;
    //获取当前日
    let date = myDate.getDate();
    let h = myDate.getHours();       //获取当前小时数(0-23)
    let m = myDate.getMinutes();     //获取当前分钟数(0-59)
    if (m < 10) m = '0' + m;
    let s = myDate.getSeconds();
    if (s < 10) s = '0' + s;
    let now = year + '-' + month + "-" + date + " " + h + ':' + m + ":" + s;
    //获取输入内容
    let oHfVal = $(this).siblings('.flex-text-wrap').find('.hf-input').val();
    console.log(oHfVal)
    let oHfName = $(this).parents('.hf-con').parents('.date-dz').siblings('.pl-text').find('.comment-size-name').html();
    let oAllVal = '回复@' + oHfName;
    if (oHfVal.replace(/^ +| +$/g, '') == '' || oHfVal == oAllVal) {

    } else {
        $.getJSON("/test?serviceType=comment", function (data) {
            let oAt = '';
            let oHf = '';
            $.each(data, function (n, v) {
                delete v.hfContent;
                delete v.atName;
                let arr;
                let ohfNameArr;
                if (oHfVal.indexOf("@") == -1) {
                    data['atName'] = '';
                    data['hfContent'] = oHfVal;
                } else {
                    arr = oHfVal.split(':');
                    ohfNameArr = arr[0].split('@');
                    data['hfContent'] = arr[1];
                    data['atName'] = ohfNameArr[1];
                }

                if (data.atName == '') {
                    oAt = data.hfContent;
                } else {
                    oAt = '回复<a href="#" class="atName">@' + data.atName + '</a> : ' + data.hfContent;
                }
                oHf = data.hfName;
            });

            let oHtml = '<div class="all-pl-con"><div class="pl-text hfpl-text clearfix"><a href="#" class="comment-size-name">我的名字 : </a><span class="my-pl-con">' + oAt + '</span></div><div class="date-dz"> <span class="date-dz-left pull-left comment-time">' + now + '</span> <div class="date-dz-right pull-right comment-pl-block"> <a href="javascript:;" class="removeBlock">删除</a> <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a> <span class="pull-left date-dz-line">|</span> <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">666</i>)</a> </div> </div></div>';
            oThis.parents('.hf-con').parents('.comment-show-con-list').find('.hf-list-con').css('display', 'block').prepend(oHtml) && oThis.parents('.hf-con').siblings('.date-dz-right').find('.pl-hf').addClass('hf-con-block') && oThis.parents('.hf-con').remove();
        });
    }
});


$('.commentAll').on('click', '.removeBlock', function () {
    let oT = $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con');
    if (oT.siblings('.all-pl-con').length >= 1) {
        oT.remove();
    } else {
        $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con').parents('.hf-list-con').css('display', 'none')
        oT.remove();
    }
    $(this).parents('.date-dz-right').parents('.date-dz').parents('.comment-show-con-list').parents('.comment-show-con').remove();

})


$('.comment-show').on('click', '.date-dz-z', function () {
    let zNum = $(this).find('.z-num').html();
    if ($(this).is('.date-dz-z-click')) {
        zNum--;
        $(this).removeClass('date-dz-z-click red');
        $(this).find('.z-num').html(zNum);
        $(this).find('.date-dz-z-click-red').removeClass('red');
    } else {
        zNum++;
        $(this).addClass('date-dz-z-click');
        $(this).find('.z-num').html(zNum);
        $(this).find('.date-dz-z-click-red').addClass('red');
    }
})
