package com.xwder.biz.app.modules.video.ffmpeg;

import cn.hutool.core.io.FileUtil;
import com.github.pagehelper.util.StringUtil;
import com.xwder.app.modules.cloud.tencent.cos.service.TencentCosService;
import com.xwder.app.modules.video.ffmpeg.CmdResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xwder
 * @Description: ffmpeg操作video
 * @date 2020/7/4 17:30
 */
@Service
public class VideoOperateService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String serviceName = "ffmpeg操作video";

    @Autowired
    private TencentCosService tencentCosService;

    /**
     * 文件转换，新文件放在: 源文件目录 + / + 源文件名 + / +newFileName
     *
     * @param sourceFile  源文件
     * @param newFileName 新文件名
     * @return
     */
    public String onvertVideo(File sourceFile, String newFileName) {
        if (!sourceFile.exists()) {
            logger.error("[{}]源文件不存在,文件地址[{}]", serviceName, sourceFile);
            return null;
        }
        // 放置新文件的路径
        String name = FileUtil.mainName(sourceFile);
        File newPath = new File(sourceFile.getParent() + File.separator + name);
        FileUtil.del(newPath);
        FileUtil.mkdir(newPath);
        String newFilePath = newPath.getPath() + File.separator + newFileName;

        List<String> commands = new ArrayList<String>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(sourceFile.getPath());
        commands.add("-codec");
        commands.add("copy");
        commands.add("-bsf:v");
        commands.add("h264_mp4toannexb");
        commands.add(newFilePath);
        CmdResult result = runCommand(commands);
        if (result.isSuccess()) {
            logger.info("[{}] 文件转换成功,新文件名称[{}]", serviceName, newFilePath);
            return newFilePath;
        }
        logger.info("[{}] 文件转换失败,错误信息[{}]", serviceName, result.getMsg());
        return null;
    }

    /**
     * 切割ts文件
     *
     * @param sourceFile ts 文件
     * @param m3u8Name   m3u8Name 名称 不带后缀 .m3u8
     * @param splitTime  切割时间
     * @return
     */
    public String splitTsFileToHLS(File sourceFile, String m3u8Name, int splitTime) {
        if (!sourceFile.exists()) {
            logger.error("[{}]源文件不存在,文件地址[{}]", serviceName, sourceFile);
            return null;
        }
        List<String> commands = new ArrayList<String>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(sourceFile.getPath());
        commands.add("-c");
        commands.add("copy");
        commands.add("-map");
        commands.add("0");
        commands.add("-f");
        commands.add("segment");
        commands.add("-segment_list");
        String m3u8FilePath = sourceFile.getParent() + File.separator + m3u8Name + ".m3u8";
        commands.add(m3u8FilePath);
        commands.add("-segment_time");
        commands.add("" + splitTime);
        commands.add(sourceFile.getParent() + File.separator + m3u8Name + "%03d.ts");
        CmdResult result = runCommand(commands);
        if (result.isSuccess()) {
            // 删除ts文件
            FileUtil.del(sourceFile);
            logger.info("[{}] 文件切割ts文件成功", serviceName);
            return m3u8FilePath;
        }
        logger.info("[{}] 文件转换失败,错误信息[{}]", serviceName, result.getMsg());
        return null;
    }

    /**
     * 切割video hls
     * 步骤： 先转换
     *
     * @param sourceFile
     * @return 转换后的文件地址, 该地址下有切割文件
     */
    public String convertAndSplitVideoHls(File sourceFile, String convertFileName, String m3u8Name, int splitTime) {
        // 先转换文件格式
        String newFilePath = onvertVideo(sourceFile, convertFileName);
        // 先转换文件格式失败
        if (newFilePath == null) {
            // 删除转换的目录
            String name = FileUtil.mainName(sourceFile);
            File newPath = new File(sourceFile.getParent() + File.separator + name);
            FileUtil.del(newPath);
            return null;
        }
        File newFile = new File(newFilePath);
        String splitResult = splitTsFileToHLS(newFile, m3u8Name, splitTime);
        // 切割文件失败
        if (StringUtil.isEmpty(splitResult)) {
            // 删除转换的目录
            String name = FileUtil.mainName(sourceFile);
            File newPath = new File(sourceFile.getParent() + File.separator + name);
            FileUtil.del(newPath);
            return null;
        }
        return newFilePath;
    }


    /**
     * 切割video hls 直接mp4 切割
     * 源文件 切割值本目录下的hls目录下
     *
     * @param sourceFile
     * @return m3u8文件地址
     */
    public String splitMp4VideoHls(File sourceFile, String m3u8Name, int splitTime) {
        if (!sourceFile.exists()) {
            logger.error("[{}]源文件不存在,文件地址[{}]", serviceName, sourceFile);
            return null;
        }
        FileUtil.mkdir(sourceFile.getParent()+File.separator+"hls");
        List<String> commands = new ArrayList<String>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add("\"" + sourceFile.getPath() + "\"");
        commands.add("-codec:v");
        commands.add("libx264");
        commands.add("-codec:a");
        commands.add("mp3");
        commands.add("-map");
        commands.add("0");
        commands.add("-f");
        commands.add("ssegment");
        commands.add("-segment_format");
        commands.add("mpegts");
        commands.add("-segment_list");
        commands.add("\"" + sourceFile.getParent() + File.separator + "hls" + File.separator + m3u8Name + ".m3u8" + "\"");
        commands.add("-segment_time");
        commands.add("" + splitTime);
        commands.add("\"" + sourceFile.getParent() + File.separator + "hls" + File.separator + m3u8Name + "%03d.ts" + "\"");
        CmdResult result = runCommand(commands);
        if (result.isSuccess()) {
            logger.info("[{}] 文件切割ts文件成功", serviceName);
            String m3u8FilePath = sourceFile.getParent() + File.separator + "hls" + File.separator + m3u8Name + ".m3u8";
            return m3u8FilePath;
        }
        logger.info("[{}] 文件转换失败,错误信息[{}]", serviceName, result.getMsg());
        return null;
    }


    /**
     * 执行Cmd命令方法
     *
     * @param command 相关命令
     * @return 执行结果
     */
    private synchronized CmdResult runCommand(List<String> command) {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        final StringBuilder stringBuilder = new StringBuilder();
        CmdResult cmdResult;
        try {
            Process process = builder.start();
            final InputStream inputStream = process.getInputStream();
            new Thread(new Runnable() {
                //启动新线程为异步读取缓冲器，防止线程阻塞
                @Override
                public void run() {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line);
                            logger.info("[{}]切割命令日志: {}",serviceName,line);
                        }
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }).start();
            process.waitFor();
            cmdResult = CmdResult.builder().success(true).msg(stringBuilder.toString()).build();
            return cmdResult;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[{}]执行命令错误,错误信息[{}],异常信息[{}]", serviceName, stringBuilder.toString(), e);
        }
        cmdResult = CmdResult.builder().success(false).msg(stringBuilder.toString()).build();
        return cmdResult;
    }
}
