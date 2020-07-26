$(document).ready(function () {
    $("#contentCardBody").children().each(function(index, element) {
        var tagName=$(this).get(0).tagName;
        if(tagName.substr(0,1).toUpperCase() === "H"){
            console.log(tagName);
            var contentH=$(this).html();//获取内容
            var markid="mark-"+tagName+"-"+index.toString();
            $(this).attr("id",markid);//为当前h标签设置id
            let spaceNum =  "";
            if (tagName === 'H1') {
                spaceNum =  "";
            } else if (tagName === 'H2') {
                spaceNum =  "";
            }  else if (tagName === 'H3') {
                spaceNum =  "1.5";
            } else if (tagName === 'H4') {
                spaceNum =  "3";
            } else if (tagName === 'H5') {
                spaceNum =  "4.5";
            } else if (tagName === 'H6') {
                spaceNum =  "6";
            }
            $("#article_navigation").append("<a href='#" + markid + "'" + " style='text-indent: " + spaceNum + "em'" + ">" + contentH + "</a>");//在目标DIV中添加内容
        }
    });
    console.log("标题导航锚点完成！");
});