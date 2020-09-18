// layui.use(['form', 'layer', 'table', 'laytpl'], function () {
//     var form = layui.form,
//         layer = parent.layer === undefined ? layui.layer : top.layer,
//         $ = layui.jquery,
//         laytpl = layui.laytpl,
//         table = layui.table;
//
//     //书籍列表
//     var tableIns = table.render({
//         elem: '#novelList',
//         url: '/sys/novel/bookList',
//         cellMinWidth: 95,
//         page: true,
//         // height : "full-120",
//         limits: [10, 15, 20, 25],
//         limit: 10,
//         id: "novelListTable",
//         cols: [[
//             {type: "checkbox", fixed: "left", width: 50},
//             {field: 'id', title: 'ID', width: 80, align: "center", fixed: "left"},
//             {field: 'bookName', title: '书籍名称', width: 200},
//             {field: 'author', title: '作者', width: 150},
//             {field: 'category', title: '分类', width: 100},
//             {field: 'updateStatus', title: '连载状态', width: 100},
//             {field: 'wordSize', title: '字数', width: 100},
//             {field: 'latestChapter', title: '最新章节', width: 150},
//             {field: 'lastUpdateTime', title: '更新时间', width: 150},
//             {field: 'bookImgSource', title: '封面', align: 'center', width: 170, templet: '#bookImages'},
//             {title: '操作', minWidth: 250, templet: '#novelListBar',fixed: "right"
//             }
//         ]],
//         done: function (res, curr, count) {
//             //图片放大预览
//             var ids;
//             for (var j in res.data) {
//                 ids = res.data[j].id;
//                 layer.photos({
//                     photos: '.thumbnailImages' + ids
//                 });
//             }
//         }
//     });
//
//
//     //列表操作
//     table.on('tool(novelList)', function (obj) {
//         var layEvent = obj.event,
//             data = obj.data;
//         //查看章节
//         if (layEvent === 'bookChapter') {
//             let url = "/sys/novel/chapterListPage.html?page=1&limit=10&bookId="+data.id;
//             document.getElementById("iframe").src="/sys/novel/chapterListPage.html?page=1&limit=10&bookId="+data.id;
//         }
//     });
//
// })
