package com.xwder.manage.modules.pic.yt.service;

import com.xwder.manage.modules.pic.yt.domain.ImageYt278com;

import java.util.List;

/**
 * 妹子图图Service接口
 * 
 * @author xwder
 * @date 2020-02-04
 */
public interface IImageYt278comService 
{
    /**
     * 查询妹子图图
     * 
     * @param id 妹子图图ID
     * @return 妹子图图
     */
    public ImageYt278com selectImageYt278comById(Long id);

    /**
     * 查询妹子图图列表
     * 
     * @param imageYt278com 妹子图图
     * @return 妹子图图集合
     */
    public List<ImageYt278com> selectImageYt278comList(ImageYt278com imageYt278com);

    /**
     * 新增妹子图图
     * 
     * @param imageYt278com 妹子图图
     * @return 结果
     */
    public int insertImageYt278com(ImageYt278com imageYt278com);

    /**
     * 修改妹子图图
     * 
     * @param imageYt278com 妹子图图
     * @return 结果
     */
    public int updateImageYt278com(ImageYt278com imageYt278com);

    /**
     * 批量删除妹子图图
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteImageYt278comByIds(String ids);

    /**
     * 删除妹子图图信息
     * 
     * @param id 妹子图图ID
     * @return 结果
     */
    public int deleteImageYt278comById(Long id);
}
