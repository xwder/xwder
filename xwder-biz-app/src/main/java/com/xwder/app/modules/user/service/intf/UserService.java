package com.xwder.app.modules.user.service.intf;

import com.xwder.app.modules.user.entity.User;

import java.util.List;

/**
 * 用户服务service
 */
public interface UserService {

    /**
     * 根据userName查找用户
     *
     * @param userName
     * @return
     */
    User getUserByUserName(String userName);

    /**
     * 根据email查找用户
     *
     * @param email
     * @return
     */
    User getUserByUserEmail(String email);

    /**
     * 根据主键ID,保存或者更新user
     *
     * @param user
     * @return
     */
    User saveOrUpdateUser(User user);

    /**
     * 登录
     *
     * @param user
     * @return
     */
    User userLogin(User user);

    /**
     * 发送邮件
     */
    void sendVerifyEmail(User user);

    /**
     * 邮箱验证操作
     *
     * @param verifyKey
     * @return
     */
    User verifyEmail(String verifyKey);

    /**
     * 从redis中获取会话信息
     *
     * @param token
     * @return
     */
    User getUserSessionFromRedis(String token);

    /**
     * 保存用户会话到redis
     *
     * @param token
     * @param user
     * @param sessionTime
     */
    void saveUserSessionToRedis(String token, User user, Integer sessionTime);

    /**
     * 删除redis用户会话信息
     *
     * @param token
     */
    void deleteRedisUserSession(String token);

    /**
     * 删除redis用户会话信息
     *
     * @param token
     * @param expireTime
     */
    void updateRedisUserSessionExpireTime(String token, Integer expireTime);


    /**
     * 查询所有的管理员
     *
     * @return
     */
    List<User> listManagerUser();
}
