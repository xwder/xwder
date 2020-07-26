$(document).ready(function () {
    $('#select-tags-advanced').selectize({
        maxItems: 15,
        plugins: ['remove_button'],
    });
});

function choosePhoto() {
    $("#previewImageUp").click();
}

function uploadPhoto(){
    var formData = new FormData();
    formData.append('file', document.getElementById('previewImageUp').files[0]);
    $.ajax({
        url:"/file/up/ue?action=uploadimage",
        type:"post",
        data: formData,
        contentType: false,
        processData: false,
        success: function(data) {
            if (data.code == 200) {
                $("#preview_photo").attr("src", data.original);

                alert("上传成功");
            } else {
                alert(JSON.stringify(data));
            }
        },
        error:function(data) {
            alert("上传失败")
        }
    });
}

/**
 * 选择文件后展示图片
 */
$("#previewImageUp").change(function() {

    //获取input file的files文件数组;
    //$('#filed')获取的是jQuery对象，.get(0)转为原生对象;
    //这边默认只能选一个，但是存放形式仍然是数组，所以取第一个元素使用[0];
    var file = $('#previewImageUp').get(0).files[0];
    //创建用来读取此文件的对象
    var reader = new FileReader();
    //使用该对象读取file文件
    reader.readAsDataURL(file);
    //读取文件成功后执行的方法函数
    reader.onload = function (e) {

        var showImg = $('#showImg');
        showImg.empty();

        //读取成功后返回的一个参数e，整个的一个进度事件
        console.log(e);
        //选择所要显示图片的img，要赋值给img的src就是e中target下result里面
        //的base64编码格式的地址
        // $('#preview_photo').src = e.target.result;
        var img = new Image();
        img.id="preview_photo";
        img.src=e.target.result;
        img.width=200;
        img.height=200;
        var showImg = document.getElementById("showImg");
        showImg.appendChild(img);
    }

});

/**
 * 清除图片
 */
function removePhoto() {
    var showImg = $('#showImg');
    showImg.empty();
}


/**
 * 发布博客
 */
function publishArticle() {
    var action = "/blog/edit/article?action=publish";
    var data = getArticleData();
    var responseData = articleOperat(action, data);
    if (responseData.code == 200) {
        // 设置文章ID
        $("articleId").val(responseData.data.id);
        window.open(responseData.data.redirectUrl);
    }
}

/**
 * 保存 或者 修改后 保存 博客
 */
function saveArticle() {
    var action = "/blog/edit/article?action=save";
    var data = getArticleData();
    var responseData = articleOperat(action, data);
    if (responseData.code == 200) {
        // 设置文章ID
        $("#articleId").val(responseData.data.id);
        alert("保存成功");
    }
}

/**
 * 预览博客文章 不保存
 */
function previewArticle() {
    var action = "/blog/article?action=preview";
    var data = getArticleData();
    var validateResult = validateArticleData(data);
    if (!validateResult) {
        return;
    }
    var responseData = articleOperat(action, data);
    if (responseData.code == 200) {
        window.open(responseData.data);
    }
}

function articleOperat(action, data) {
    var resultData;
    $.ajax({
        type: "post",
        url: action,
        traditional: true,
        async: false,
        data: {paramData: JSON.stringify(data)},
        success: function (data) {
            console.log("data: " + data.code)
            if (data.code == 200) {
                console.log("操作成功" + data.data);
            } else {
                console.log("操作失败" + data.message);
                alert("操作失败,错误信息: "+ data.code + " " + data.message);
            }
            resultData = data;
        }
    });
    return resultData;
}

/**
 * 获取表单参数
 */
function getArticleData() {

    var articleId = $("#articleId").val();

    var articleCategory = $("input:checkbox[name='articleCategory']:checked");
    console.log("articleCategory: " + articleCategory.val());

    var packageCodeList = new Array();
    $("#select-tags-advanced option:selected").each(function () {
        packageCodeList.push($(this).val());//向数组中添加元素
    });
    console.log("packageCodeList: " + packageCodeList.toString());

    var title = $("#articleTitle").val();
    var summary = $("#articleSummary").val();
    var content = UE.getEditor('editor').getContent();
    var type = $("input:checkbox[name='articleType']:checked").val();
    var category = $("input:checkbox[name='articleCategory']:checked").val();

    var previewImgUrl = '';
    if( $("#preview_photo").length != 0 ){
        previewImgUrl = $("#preview_photo")[0].src;
    }

    var postData = {
        "id": articleId,
        "title": title,
        "summary": summary,
        "content": content,
        "type": type,
        "category": category,
        "tags": packageCodeList,
        "previewImgUrl": previewImgUrl
    }
    return postData;
}

/**
 * 文章内容提交之前的校验
 * @param data
 * @returns {boolean}
 */
function validateArticleData(data) {
    if (data.title.length < 3) {
        alert("标题长度不能小于3");
        setTimeout(function () {
            $("#articleTitle").focus();
        }, 50)
        return false;
    }
    if (data.summary.length < 3) {
        alert("摘要长度不能小于3");
        setTimeout(function () {
            $("#articleSummary").focus();
        }, 50)
        return false;
    }
    // 有没有内容
    if (data.content.length < 3) {
        alert("请输入文章内容");
        UE.getEditor('editor').focus();
        return false;
    }
    // type
    if (data.type.length < 1) {
        alert("请选择文章类型");
        return false;
    }
    // category
    if (typeof(data.category) == "undefined") {
        alert("请选择文章分类");
        return false;
    }
    return true;
}

/**
 * 文章分类单选
 */
$(".articleCategory").click(function () {
    $(".articleCategory").find("input[type='checkbox']").prop("checked", false);//每次点击前，将所有checkbox置为 未选中
    var cobj = $(this).find("input[type='checkbox']");//当前点击的checkbox
    cobj.prop("checked", true);//将当前点击的checkbox置为选中状态
})

/**
 * 文章类型单选
 */
$(".articleType").click(function () {
    $(".articleType").find("input[type='checkbox']").prop("checked", false);//每次点击前，将所有checkbox置为 未选中
    var cobj = $(this).find("input[type='checkbox']");//当前点击的checkbox
    cobj.prop("checked", true);//将当前点击的checkbox置为选中状态
})