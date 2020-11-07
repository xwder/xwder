package com.xwder.app.modules.user.repositry;

import com.xwder.app.modules.user.entity.UserQQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wande
 * @version 1.0
 * @date 2020-11-07 22:48:55
 */
@Repository
public interface UserQQRepositry extends JpaRepository<UserQQ, Integer> {

    /**
     * 根据openid查找QQ用户信息
     *
     * @param openId
     * @return
     */
    UserQQ findByOpenId(String openId);

}
