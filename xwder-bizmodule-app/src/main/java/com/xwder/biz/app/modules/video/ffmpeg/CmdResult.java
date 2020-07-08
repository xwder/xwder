package com.xwder.biz.app.modules.video.ffmpeg;

import lombok.Builder;
import lombok.Data;

/**
 * @author xwder
 * @Description: cmd命令执行返回结果
 * @date 2020/7/4 17:39
 */
@Builder
@Data
public class CmdResult {
    private boolean success;
    private String msg;
}
