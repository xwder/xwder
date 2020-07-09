package com.xwder.manage.modules.pic.plmm.service;

import com.xwder.manage.modules.pic.plmm.domain.ImagePlmm;
import java.util.List;

/**
 * 美女图片Service接口
 * 
 * @author xwder
 * @date 2020-02-07
 */
public interface IImagePlmmService 
{
    /**
     * 查询美女图片
     * 
     * @param id 美女图片ID
     * @return 美女图片
     */
    public ImagePlmm selectImagePlmmById(Long id);

    /**
     * 查询美女图片列表
     * 
     * @param imagePlmm 美女图片
     * @return 美女图片集合
     */
    public List<ImagePlmm> selectImagePlmmList(ImagePlmm imagePlmm);

    /**
     * 新增美女图片
     * 
     * @param imagePlmm 美女图片
     * @return 结果
     */
    public int insertImagePlmm(ImagePlmm imagePlmm);

    /**
     * 修改美女图片
     * 
     * @param imagePlmm 美女图片
     * @return 结果
     */
    public int updateImagePlmm(ImagePlmm imagePlmm);

    /**
     * 批量删除美女图片
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteImagePlmmByIds(String ids);

    /**
     * 删除美女图片信息
     * 
     * @param id 美女图片ID
     * @return 结果
     */
    public int deleteImagePlmmById(Long id);
}
