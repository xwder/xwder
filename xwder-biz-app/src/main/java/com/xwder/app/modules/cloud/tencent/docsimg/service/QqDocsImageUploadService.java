package com.xwder.app.modules.cloud.tencent.docsimg.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.*;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.modules.cloud.tencent.cos.service.TencentCosService;
import com.xwder.app.utils.DateUtil;
import com.xwder.app.utils.FileNameUtils;
import com.xwder.cloud.commmon.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * qq docs 图片上传api
 *
 * @author wande
 * @version 1.0
 * @date 2020/08/31
 */
@Slf4j
@Service
public class QqDocsImageUploadService {

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Value("${tencent.docs.cookie:}")
    private String cookie;

    @Value("${tencent.docs.imageUploadUrl:}")
    private String imageUploadUrl;


    @Autowired
    private TencentCosService tencentCosService;


    /**
     * qq docs 图片上传api
     *
     * @param saveLocal     1-保存到服务器 2-不保存到服务器
     * @param uploadCos     1-图片是否上传cos 2-不上传cos
     * @param uid           腾讯文档 身份认证 cookie uid
     * @param uidKey        腾讯文档 身份认证 cookie uidKey
     * @param multipartFile
     * @return
     */
    public Map qqDocsImageUpload(Integer saveLocal, Integer uploadCos, String uid, String uidKey, MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String[] splits = fileName.split("\\.");
        String fileSuffix = splits.length >= 2 ? splits[splits.length - 1] : "";
        String uploadDir =
                "docs" + File.separator + "image" + File.separator + DateUtil.formatDate(new Date(), "yyyy")
                        + File.separator + DateUtil.formatDate(new Date(), "MM")
                        + File.separator + DateUtil.formatDate(new Date(), "dd")
                        + File.separator + FileNameUtils.randomFileNameWithTime() + "." + fileSuffix;
        String saveUpload = sysConfigAttribute.getUploadSaveBaseDir() + File.separator + uploadDir;

        File saveFile = new File(saveUpload);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }

        try {
            multipartFile.transferTo(saveFile);
            log.info("保存文件 [{}] 成功", saveUpload);
        } catch (IOException e) {
            log.error("保存文件失败", e);
            return Maps.newHashMap();
        }

        // 如果没有uid 则用系统默认的
        String uploadAuthCookie = "";
        if (StrUtil.isNotEmpty(uid) && StrUtil.isNotEmpty(uidKey)) {
            uploadAuthCookie = "uid=" + uid + ";" + "uid_key=" + uidKey + ";";
        } else {
            uploadAuthCookie = cookie;
        }


        HashMap<String, Object> paramMap = new HashMap<>();
        //文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
        paramMap.put("file", FileUtil.file(saveFile));
        //链式构建请求
        HttpResponse httpResponse = HttpRequest.post(imageUploadUrl)
                //头信息，多个头信息多次调用此方法即可
                .header(Header.COOKIE, uploadAuthCookie)
                .form(paramMap)
                .timeout(20000)
                .execute();
        Map resultMap = new HashMap();
        if (httpResponse.getStatus() == HttpStatus.HTTP_OK) {
            String result = httpResponse.body();
            log.info("上传图片到腾讯文档成功 响应信息 [{}]", result);
            resultMap = JSONUtil.toBean(result, HashMap.class);
        } else {
            log.error("上传图片到腾讯文档失败 响应码 [{}] 响应信息 [{}]", httpResponse.getStatus(), httpResponse.body());
        }

        // 上传到腾讯cos
        if (uploadCos == 1) {
            uploadDir = uploadDir.replace("\\", "/");
            String cosUploadResult = tencentCosService.uploadTenCos(saveFile, uploadDir, sysConfigAttribute.getTencentCosBucketName(), sysConfigAttribute.getTencentCosRegion());
            if (StrUtil.isEmpty(cosUploadResult)) {
                log.error("保存文件[{}]到cos失败", saveFile.getName());
                // 删除磁盘文件
                FileUtil.del(saveFile);
                resultMap.put("state", "上传对象存储失败");
                return resultMap;
            }
            String fileCdnUrl = sysConfigAttribute.getTencentCdnPreFixUrl() + "/" + uploadDir;
            resultMap.put("fileCdnUrl", fileCdnUrl);
            log.info("保存文件[{}]到cos成功，cdn访问地址[{}]", saveFile.getName(), fileCdnUrl);
        }

        // 保存到服务器
        if (saveLocal == 1) {
        } else {
            // 删除磁盘文件
            FileUtil.del(saveFile);
        }

        return resultMap;
    }
}
