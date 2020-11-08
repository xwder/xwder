package com.xwder.app.modules.user.repositry;

import com.xwder.app.modules.user.entity.UserGithub;
import com.xwder.app.modules.user.entity.UserQQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wande
 * @version 1.0
 * @date 2020-11-07 22:48:55
 */
@Repository
public interface UserGithubRepositry extends JpaRepository<UserGithub, Integer> {

    /**
     * 根据 login github用户名查找用户信息
     *
     * @param login
     * @return
     */
    UserGithub findByLogin(String login);

}
