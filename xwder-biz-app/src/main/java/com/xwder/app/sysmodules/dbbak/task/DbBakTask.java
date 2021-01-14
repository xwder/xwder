package com.xwder.app.sysmodules.dbbak.task;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.xwder.app.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 有问题
 * 数据库备份定时任务 每天1点备份指定表并将备份文件email到指定邮箱
 *
 * @author xwder
 * @date 2021/1/12 16:45
 */
@Slf4j
@Component(value = "dbBakTask")
public class DbBakTask {

    private String host = "smtp.163.com";
    private int port = 25;
    private String from = "xwdercom@163.com";
    private String user = "xwdercom@163.com";
    private String pass = "Jb9rU4U95vw4hpMq";
    private String accept = "xwder@xwder.com";

    /**
     * 备份数据库 定时任务入口
     */
    public void dbBakAndSendMailServiceEntry() {
        log.info("备份数据库开始");
        // 获取备份的表
        List<String> backTableList = getBackTableList();
        // 备份文件路径信息
        String bakFileNamePath = "/usr/local/xwder/sqlbak/xwder-"
                + DateUtil.formatDate(new Date(), DateUtil.format_yyyyMMddHHmmssSSS) + ".sql.gz";
        execBakDbCommand(backTableList, bakFileNamePath);
        sendBakFileMail(bakFileNamePath);
        log.info("备份数据库结束");
    }

    /**
     * 发送备份文件到指定邮箱
     *
     * @param bakFileNamePath
     */
    private void sendBakFileMail(String bakFileNamePath) {
        File file = new File(bakFileNamePath);
        if ((!file.isFile() || !file.exists())) {
            log.error("数据库备份-备份文件[{}]不存在", bakFileNamePath);
            return;
        }
        MailAccount account = new MailAccount();
        account.setHost(host);
        account.setPort(port);
        account.setAuth(true);
        account.setFrom(from);
        account.setUser(user);
        account.setPass(pass);
        String subject = "xwder-数据库备份";
        String content = "xwder-数据库备份";
        MailUtil.send(account, CollUtil.newArrayList(accept), subject, content, false, file);
        log.info("数据库备份成功-备份文件[{}]", bakFileNamePath);
    }


    /**
     * 执行备份命令
     *
     * @return 备份文件路径信息
     */
    private String execBakDbCommand(List<String> backTableList, String bakFileNamePath) {
        String cmd = "mysqldump -uxwder -h120.92.164.184 -pwvDwERwidZZJhfh2 --databases xwder " +
                "--tables %s --single-transaction --set-gtid-purged=off --max_allowed_packet=512M  --skip-lock-tables | gzip > %s";
        String tableName = backTableList.stream().collect(Collectors.joining(" ", "", ""));
        cmd = String.format(cmd, tableName, bakFileNamePath);
        log.info("备份数据库-备份命令：{}", cmd);
        boolean cmdResult = runCommand(cmd);
        if (cmdResult) {
            log.info("备份数据库-成功,备份文件{}", bakFileNamePath);
        } else {
            log.error("备份数据库-失败,错误信息");
            return null;
        }
        return bakFileNamePath;
    }

    /**
     * 备份指定表
     *
     * @return
     */
    private List<String> getBackTableList() {
        List<String> tableNames = new ArrayList<>(16);
        try {
            ClassPathResource resource = new ClassPathResource("bak_tables.txt");
            InputStream inputStream = resource.getInputStream();
            IoUtil.readUtf8Lines(inputStream, tableNames);
        } catch (IOException e) {
            log.info("数据库备份-获取备份数据库表文件错误", e);
        }
        return tableNames;
    }


    /**
     * 执行Cmd命令方法
     *
     * @param command 相关命令
     * @return 执行结果
     */
    private synchronized boolean runCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            //0 表示线程正常终止。
            if (process.waitFor() == 0) {
                return true;
            }
        } catch (Exception e) {
            log.error("数据库备份-执行备份命令错误[{}]", command, e);
            e.printStackTrace();
            return false;
        }
        log.error("数据库备份-执行备份命令错误[{}]", command);
        return false;
    }


}
