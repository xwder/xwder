package com.xwder.app.modules.user.repositry;

import com.xwder.app.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/09
 */
@Repository
public interface UserRepositry extends JpaRepository<User, Integer> {

    /**
     * 根据userName查找用户
     *
     * @param userName
     * @return
     */
    List<User> findAllByUserName(String userName);

    /**
     * 根据email查找用户
     *
     * @param email
     * @return
     */
    List<User> findAllByEmail(String email);

    /**
     * 查询管理员
     *
     * @param isManager
     * @return
     */
    List<User> findAllByManager(Integer isManager);

    /**
     * 根据openid查找QQ用户信息
     *
     * @param openId
     * @return
     */
    User findByOpenId(String openId);
}
