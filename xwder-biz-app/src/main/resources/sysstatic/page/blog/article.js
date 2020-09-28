layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    let form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //博客分类列表
    let tableIns = table.render({
        elem: '#blogArticleList',
        url: '/sys/blog/articleList',
        cellMinWidth: 95,
        page: true,
        limits: [10, 15, 20, 25],
        limit: 10,
        autoSort: true,
        cols: [
            [
                {field: 'id', title: 'ID', align: "center", fixed: "left"},
                {field: 'userName', title: '用户名'},
                {field: 'categoryName', title: '分类', minWidth: 150},
                {field: 'title', title: '标题', minWidth: 300},
                {field: 'summary', title: '摘要'},
                {field: 'statusDesc', title: '状态'},
                {
                    field: 'previewImage', title: '预览图',
                    templet: function (d) {
                        return '<div ><img src="' + d.previewImage + '" alt="" width="50px" height="50px" onclick="showBigImage(this)"></a></div>';
                    }
                },
                {field: 'readCount', title: '阅读数'},
                {field: 'comments', title: '评论数'},
                {field: 'likes', title: '点赞数'},
                {field: 'favors', title: '收藏数',},
                {field: 'modifieCount', title: '编辑次数'},
                {field: 'articleType', title: '原创转载'},
                {field: 'publishTime', title: '发布时间', minWidth: 180},
                {
                    title: '操作', minWidth: 200, templet: '#articleListBar', fixed: "right"
                }
            ]
        ],
        parseData: function (res) {
            if (res.code == 200) {
                let data = {
                    "code": 0,
                    "msg": res.message,
                    "count": res.data.totalElements,
                    "data": res.data.content
                };
                return data;
            }
        },
    });

    //列表操作
    table.on('tool(blogArticleList)', function (obj) {
        let layEvent = obj.event;
        let article = obj.data;
        // 修改博客
        if (layEvent === 'editArticle') {
            editArticle(article)
        }
    });

    // 查询定时任务
    $(".query").click(function () {
        tableIns.reload();
    });

    // 新增博客
    $(".addArticle").click(function () {
        editArticle();
    });

    function editArticle(article) {
        /*let id = '';
        console.log(((typeof (article) == undefined)));
        if (!(typeof (article) == undefined)) {
            id = article.id;
        }*/

        let id = "";
        if (!(typeof (article) == "undefined")) {
            id = article.id;
        }
        let index = layer.open({
            type: 2,
            title: '编辑文章',
            shadeClose: true,
            shade: false,
            maxmin: true, //开启最大化最小化按钮
            area: ['1200px', '800px'],
            content: '/sys/blog/articleEdit.html?id=' + id,
            success: function (layero, index) {
                let body = layer.getChildFrame('body', index);
                if (article) {
                    body.find(".title").val(article.title);
                    body.find(".summary").val(article.summary);
                    body.find(".previewImage").attr("src", article.previewImage);
                    body.find(".status input[value=" + article.status + "]").prop("checked", "checked"); //是否发布
                    body.find(".articleType input[value=" + article.articleType + "]").prop("checked", "checked"); //类型
                }
                form.render('select');
                form.render();
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        });
        // layer.full(index);
    }
})

let $ = layui.jquery;

/**
 * @param e 图片对象
 */
function showBigImage(e) {
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        shadeClose: true, //点击阴影关闭
        area: [$(e).width + 'px', $(e).height + 'px'], //宽高
        content: "<img src=" + $(e).attr('src') + " />"
    });
}