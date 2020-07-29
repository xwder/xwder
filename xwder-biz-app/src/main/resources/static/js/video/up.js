//上传文件的话 得 单独出来
function test1(shardIndex) {
    console.log(shardIndex);


    //获取表单中的file
    var file = $('#inputfile').get(0).files[0];
    //文件分片 以20MB去分片
    var shardSize = 20 * 1024 * 1024;
    //定义分片索引
    var shardIndex = shardIndex;
    //定义分片的起始位置
    var start = (shardIndex - 1) * shardSize;
    //定义分片结束的位置 file哪里来的?
    var end = Math.min(file.size, start + shardSize);
    //从文件中截取当前的分片数据
    var fileShard = file.slice(start, end);
    //分片的大小
    var size = file.size;
    //总片数
    var shardTotal = Math.ceil(size / shardSize);
    //文件的后缀名
    var fileName = file.name;
    var suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
    //把视频的信息存储为一个字符串
    var filedetails = file.name + file.size + file.type + file.lastModifiedDate;
    //使用当前文件的信息用md5加密生成一个key 这个加密是根据文件的信息来加密的 如果相同的文件 加的密还是一样的
    var key = hex_md5(filedetails);
    var key10 = parseInt(key, 16);
    //把加密的信息 转为一个64位的
    var key62 = Tool._10to62(key10);

    var formData = new FormData();
    formData.append('file', fileShard);
    formData.append('suffix', suffix);
    formData.append('shardIndex', parseInt(shardIndex));
    formData.append('shardSize', parseInt(shardSize));
    formData.append('shardTotal', parseInt(shardTotal));
    formData.append('size', parseInt(size));
    formData.append("key", key62)
    formData.append("sourceName", fileName)
    $.ajax({
        url: "/video/upload",
        type: "post",
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data.code == 200) {
                //这里应该是一个递归调用
                if (shardIndex < shardTotal) {
                    var index = shardIndex + 1;
                    test1(index);
                }
            }else {
                alert(data)
            }
        },
        error: function () {
            //请求出错处理
        }
    })
}

//判断这个加密文件存在不存在
function check() {
    var file = $('#inputfile').get(0).files[0];
    //把视频的信息存储为一个字符串
    var filedetails = file.name + file.size + file.type + file.lastModifiedDate;
    //使用当前文件的信息用md5加密生成一个key 这个加密是根据文件的信息来加密的 如果相同的文件 加的密还是一样的
    var key = hex_md5(filedetails);
    var key10 = parseInt(key, 16);
    //把加密的信息 转为一个64位的
    var key62 = Tool._10to62(key10);
    //检查这个key存在不存在
    $.ajax({
        url: "/video/checkUploaded",
        type: "post",
        data: {'fileKey': key62},
        success: function (data) {
            console.log(data);
            if (data.code == 404) {
                //这个方法必须抽离出来
                test1(1);
            } else if(data.code == 200 && data.data.shardIndex == data.data.shardTotal){
                alert("极速上传成功");
            }else {
                //找到这个是第几片 去重新上传
                test1(parseInt(data.data.shardIndex));
            }
        }
    })
}
