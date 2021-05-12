package com.xwder.app.modules.picture.repository;

import com.xwder.app.common.jpa.repository.BaseJpaRepository;
import com.xwder.app.modules.picture.entity.Picture;
import org.springframework.stereotype.Repository;

/**
 * @author xwder
 * @date 2021/5/12 14:38
 **/
@Repository
public interface PictureRepository extends BaseJpaRepository<Picture, Long> {
}
