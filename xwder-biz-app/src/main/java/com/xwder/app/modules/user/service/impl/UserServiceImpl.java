package com.xwder.app.modules.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.RedisConstant;
import com.xwder.app.consts.SysConstant;
import com.xwder.app.consts.UserStatusEnum;
import com.xwder.app.config.mq.MQProducerMessage;
import com.xwder.app.config.mq.RabbitConfig;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.user.repositry.UserRepositry;
import com.xwder.app.modules.user.service.intf.UserService;
import com.xwder.app.utils.RedisUtil;
import com.xwder.app.utils.UpdateUtil;
import com.xwder.cloud.commmon.constan.MailTypeConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户访问实现类
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/09
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MQProducerMessage mqProducerMessage;

    @Autowired
    private RabbitConfig rabbitConfig;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserRepositry userRepositry;

    /**
     * 根据userName查找用户
     *
     * @param userName
     * @return
     */
    @Override
    public User getUserByUserName(String userName) {
        List<User> userList = userRepositry.findAllByUserName(userName);
        if (CollectionUtil.isNotEmpty(userList)) {
            log.info("查询用户id[{}]已存在", userName);
            return userList.get(0);
        }
        log.info("查询用户id[{}]不存在", userName);
        return null;
    }

    /**
     * 根据email查找用户
     *
     * @param email
     * @return
     */
    @Override
    public User getUserByUserEmail(String email) {
        List<User> userList = userRepositry.findAllByEmail(email);
        if (CollectionUtil.isNotEmpty(userList)) {
            log.info("查询用户email[{}]已存在", email);
            return userList.get(0);
        }
        log.info("查询用户email[{}]不存在", email);
        return null;
    }

    /**
     * 根据主键ID,保存或者更新user
     *
     * @param user
     * @return 操作成功返回user 操作失败返回null
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User saveOrUpdateUser(User user) {
        // id 为 null 且用户名不存在
        if (user.getId() == null) {
            // 注册逻辑
            // 设置用户基础参数值
            String salt = RandomStringUtils.randomAlphanumeric(16);
            String password = Md5Crypt.apr1Crypt(user.getPassword().getBytes(), salt);
            user.setSalt(salt);
            user.setPassword(password);
            // 注册时间
            Date nowDate = new Date();
            user.setRegisterTime(nowDate);
            // 上次修改个人信息时间
            user.setLastUpdateTime(nowDate);
            // 用户状态
            user.setStatus(UserStatusEnum.UN_VERIFY.getCode());
            // 设置未删除状态
            user.setAvailable(1);
            user = userRepositry.save(user);
            log.info("保存新注册用户[{}]成功", user.getUserName());
            user.setSalt(null);
            user.setPassword(null);
            // 发送激活账户邮件
            sendVerifyEmail(user);
        } else {
            String userName = user.getUserName();
            List<User> userList = userRepositry.findAllByUserName(userName);
            if (userList == null && userList.size() != 1) {
                log.error("该用户不存在");
                return null;
            }
            User sourceUser = userList.get(0);
            // 忽略掉密码相关信息
            sourceUser.setPassword(null);
            sourceUser.setSalt(null);
            UpdateUtil.copyNullProperties(user, sourceUser);
            userRepositry.save(sourceUser);
            log.info("更新用户[{}]信息成功", sourceUser.getUserName());
            user = sourceUser;
        }

        user.setSalt(null);
        user.setPassword(null);
        return user;
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @Override
    public User userLogin(User user) {
        String userName = user.getUserName();
        User sourceUser = getUserByUserName(userName);
        if (sourceUser == null) {
            return null;
        }
        String loginPassWord = Md5Crypt.apr1Crypt(user.getPassword().getBytes(), sourceUser.getSalt());
        if (StringUtils.equalsAnyIgnoreCase(sourceUser.getPassword(), loginPassWord)) {
            sourceUser.setPassword(null);
            sourceUser.setSalt(null);
            log.info("用户[{}]登录成功", userName);
            return sourceUser;
        }
        log.info("用户[{}]登录失败", userName);
        return null;
    }

    /**
     * 发送邮件
     */
    @Override
    public void sendVerifyEmail(User user) {

        // 凭借激活url参数
        String key = RandomStringUtils.randomAlphanumeric(32);
        String verifyUrl = sysConfigAttribute.getMailVerifyUrl() + "?verifyKey=" + key;
        String htmlContent = "<p>欢迎注册 </p><h1>%s</h1> 邮件激活链接半个小时内有效<p><a href=\"%s\">激活请点击</a></p>";
        String content = String.format(htmlContent, sysConfigAttribute.getChineseName(), verifyUrl);
        String subject = "邮箱验证";

        Map mailMap = Maps.newHashMap();
        mailMap.put("mailType", MailTypeConstant.HTMLMAIL);
        mailMap.put("to", user.getEmail());
        mailMap.put("subject", subject);
        mailMap.put("content", content);
        String jsonContent = JSONUtil.toJsonStr(mailMap);
        mqProducerMessage.sendMsg(jsonContent, rabbitConfig.getXwderExchageEmail(), rabbitConfig.getXwderEmailVerifyEmailRoutingkey());
        log.info("用户[{}]发送邮箱验证成功", user.getUserName());

        // ehcache缓存 key 和 用户userName
        String redisKey = RedisConstant.EMAIL_VERIFY_KEY + ":" + key;
        redisUtil.set(redisKey, user, RedisConstant.EMAIL_VERIFY_KEY_CACHETIME);

        // 发送消息给管理员由用户注册了
        if (sysConfigAttribute.getNewUserReigsterWeChatNoticeAdmin()) {
            String wxPusherContent = String.format("新用户注册                                        " +
                            "\n用户名:%s\n邮箱:%s\n电话:%s",
                    user.getUserName(), user.getEmail(), user.getPhone());
            HashMap<String, Object> pusherMap = Maps.newHashMap();
            pusherMap.put("uid", sysConfigAttribute.getAdminWxPusherUid());
            pusherMap.put("msg", wxPusherContent);
            jsonContent = JSONUtil.toJsonStr(pusherMap);
            mqProducerMessage.sendMsg(jsonContent, rabbitConfig.getXwderExchageWeChat(), rabbitConfig.getXwderWeChatChapterUpdateRoutingkey());
            log.info("新用户[{}]注册，给管理员发送微信通知成功,通知消息内容:{}", user.getUserName(), wxPusherContent);
        }
    }


    /**
     * 邮箱验证操作
     *
     * @param verifyKey
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User verifyEmail(String verifyKey) {
        String redisKey = RedisConstant.EMAIL_VERIFY_KEY + ":" + verifyKey;
        User user = (User) redisUtil.get(redisKey);

        if (user == null) {
            return null;
        }
        user.setStatus(UserStatusEnum.EMAIL_VERIFY.getCode());
        saveOrUpdateUser(user);
        log.info("验证用户[{}]邮箱成功", user.getUserName());
        // 清除缓存
        redisUtil.del(redisKey);
        return user;
    }

    /**
     * 从redis中获取会话信息
     *
     * @param token
     * @return
     */
    @Override
    public User getUserSessionFromRedis(String token) {
        String userSessionRedisKey = SysConstant.USER_SESSION_REDIS_KEY + ":" + token;
        User user = (User) redisUtil.get(userSessionRedisKey);
        return user;
    }

    /**
     * 保存用户会话到redis
     *
     * @param token
     * @param user
     * @param sessionTime
     */
    @Override
    public void saveUserSessionToRedis(String token, User user, Integer sessionTime) {
        String userSessionRedisKey = SysConstant.USER_SESSION_REDIS_KEY + ":" + token;
        redisUtil.set(userSessionRedisKey, user, sessionTime);
    }

    /**
     * 删除redis用户会话信息
     *
     * @param token
     */
    @Override
    public void deleteRedisUserSession(String token) {
        String userSessionRedisKey = SysConstant.USER_SESSION_REDIS_KEY + ":" + token;
        redisUtil.del(userSessionRedisKey);
    }

    /**
     * 删除redis用户会话信息
     *
     * @param token
     * @param expireTime
     */
    @Override
    @Async("taskExecutor")
    public void updateRedisUserSessionExpireTime(String token, Integer expireTime) {
        String userSessionRedisKey = SysConstant.USER_SESSION_REDIS_KEY + ":" + token;
        redisUtil.expire(userSessionRedisKey, expireTime);
    }

    /**
     * 查询所有的管理员
     *
     * @return
     */
    @Override
    public List<User> listManagerUser() {
        List<User> managerUserList = userRepositry.findAllByManager(1);
        managerUserList.forEach(user -> {
            user.setPassword(null);
            user.setSalt(null);
            user.setEmail(null);
            user.setPhone(null);
        });
        return managerUserList;
    }
}
