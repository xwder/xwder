package com.xwder.manage.modules.pic.yt.service.impl;

import java.util.List;

import com.xwder.manage.modules.pic.yt.domain.ImageYt278com;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xwder.manage.modules.pic.yt.mapper.ImageYt278comMapper;
import com.xwder.manage.modules.pic.yt.service.IImageYt278comService;
import com.xwder.manage.common.core.text.Convert;

/**
 * 妹子图图Service业务层处理
 * 
 * @author xwder
 * @date 2020-02-04
 */
@Service
public class ImageYt278comServiceImpl implements IImageYt278comService 
{
    @Autowired
    private ImageYt278comMapper imageYt278comMapper;

    /**
     * 查询妹子图图
     * 
     * @param id 妹子图图ID
     * @return 妹子图图
     */
    @Override
    public ImageYt278com selectImageYt278comById(Long id)
    {
        return imageYt278comMapper.selectImageYt278comById(id);
    }

    /**
     * 查询妹子图图列表
     * 
     * @param imageYt278com 妹子图图
     * @return 妹子图图
     */
    @Override
    public List<ImageYt278com> selectImageYt278comList(ImageYt278com imageYt278com)
    {
        return imageYt278comMapper.selectImageYt278comList(imageYt278com);
    }

    /**
     * 新增妹子图图
     * 
     * @param imageYt278com 妹子图图
     * @return 结果
     */
    @Override
    public int insertImageYt278com(ImageYt278com imageYt278com)
    {
        return imageYt278comMapper.insertImageYt278com(imageYt278com);
    }

    /**
     * 修改妹子图图
     * 
     * @param imageYt278com 妹子图图
     * @return 结果
     */
    @Override
    public int updateImageYt278com(ImageYt278com imageYt278com)
    {
        return imageYt278comMapper.updateImageYt278com(imageYt278com);
    }

    /**
     * 删除妹子图图对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteImageYt278comByIds(String ids)
    {
        return imageYt278comMapper.deleteImageYt278comByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除妹子图图信息
     * 
     * @param id 妹子图图ID
     * @return 结果
     */
    @Override
    public int deleteImageYt278comById(Long id)
    {
        return imageYt278comMapper.deleteImageYt278comById(id);
    }
}
