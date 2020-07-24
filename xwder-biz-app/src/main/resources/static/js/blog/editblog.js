$(document).ready(function () {
    $('#select-tags-advanced').selectize({
        maxItems: 15,
        plugins: ['remove_button'],
    });
});

function publishArticle() {
    var action = "/blog/article/save?action=publish";
    var data = getArticleData();
    var responseData = articleOperat(action, data);
    if (responseData.code == 200) {
        // 设置文章ID
        $("articleId").val(responseData.data.id);
        window.open(responseData.data.redirectUrl);
    }
}

function saveArticle() {
    var action = "/blog/article/save?action=save";
    var data = getArticleData();
    var responseData = articleOperat(action, data);
    if (responseData.code == 200) {
        // 设置文章ID
        $("#articleId").val(responseData.data.id);
        alert("保存成功");
    }
}

function previewArticle() {
    var action = "/blog/article/save?action=preview";
    var data = getArticleData();
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

    var postData = {
        "id": articleId,
        "title": title,
        "summary": summary,
        "content": content,
        "type": type,
        "category": category,
        "tags": packageCodeList
    }
    return postData;
}
