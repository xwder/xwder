layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    //书籍列表
    var tableIns = table.render({
        elem: '#chapterList',
        url: '/sys/novel/chapterList',
        cellMinWidth: 95,
        page: true,
        // height : "full-120",
        limits: [10, 15, 20, 25],
        limit: 10,
        id: "novelListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'id', title: 'ID', width: 80, align: "center", fixed: "left"},
            {field: 'bookName', title: '书籍名称', width: 200},
            {field: 'author', title: '作者', width: 100},
            {field: 'chapterName', title: '章节名称', width: 200},
            {field: 'chapterWordSize', title: '章节字数', width: 100},
            {field: 'updateTime', title: '更新时间', width: 200},
            {field: 'gmtCreate', title: '收录时间', width: 200},
            {title: '操作', minWidth: 150, templet: '#chapterListBar',fixed: "right",align: "center"
            }
        ]],
    });


    //列表操作
    table.on('tool(chapterList)', function (obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'edit') { //编辑
            addUser(data);
        } else if (layEvent === 'usable') { //启用禁用
            var _this = $(this),
                usableText = "是否确定禁用此用户？",
                btnText = "已禁用";
            if (_this.text() == "已禁用") {
                usableText = "是否确定启用此用户？",
                    btnText = "已启用";
            }
            layer.confirm(usableText, {
                icon: 3,
                title: '系统提示',
                cancel: function (index) {
                    layer.close(index);
                }
            }, function (index) {
                _this.text(btnText);
                layer.close(index);
            }, function (index) {
                layer.close(index);
            });
        } else if (layEvent === 'del') { //删除
            layer.confirm('确定删除此用户？', {
                icon: 3,
                title: '提示信息'
            }, function (index) {
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            });
        }
    });

})
