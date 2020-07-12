package com.xwder.app.modules.user.repositry;

import com.xwder.app.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/09
 */
public interface UserRepositry extends JpaRepository<User, Integer> {

    /**
     * 根据UserId查找用户
     *
     * @param userId
     * @return
     */
    List<User> findAllByUserId(String userId);

    /**
     * 根据email查找用户
     *
     * @param email
     * @return
     */
    List<User> findAllByEmail(String email);

}
