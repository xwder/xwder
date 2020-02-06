package com.xwder.manage.modules.pic.plmm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xwder.manage.modules.pic.plmm.mapper.ImagePlmmMapper;
import com.xwder.manage.modules.pic.plmm.domain.ImagePlmm;
import com.xwder.manage.modules.pic.plmm.service.IImagePlmmService;
import com.xwder.manage.common.core.text.Convert;

/**
 * 美女图片Service业务层处理
 * 
 * @author xwder
 * @date 2020-02-07
 */
@Service
public class ImagePlmmServiceImpl implements IImagePlmmService 
{
    @Autowired
    private ImagePlmmMapper imagePlmmMapper;

    /**
     * 查询美女图片
     * 
     * @param id 美女图片ID
     * @return 美女图片
     */
    @Override
    public ImagePlmm selectImagePlmmById(Long id)
    {
        return imagePlmmMapper.selectImagePlmmById(id);
    }

    /**
     * 查询美女图片列表
     * 
     * @param imagePlmm 美女图片
     * @return 美女图片
     */
    @Override
    public List<ImagePlmm> selectImagePlmmList(ImagePlmm imagePlmm)
    {
        return imagePlmmMapper.selectImagePlmmList(imagePlmm);
    }

    /**
     * 新增美女图片
     * 
     * @param imagePlmm 美女图片
     * @return 结果
     */
    @Override
    public int insertImagePlmm(ImagePlmm imagePlmm)
    {
        return imagePlmmMapper.insertImagePlmm(imagePlmm);
    }

    /**
     * 修改美女图片
     * 
     * @param imagePlmm 美女图片
     * @return 结果
     */
    @Override
    public int updateImagePlmm(ImagePlmm imagePlmm)
    {
        return imagePlmmMapper.updateImagePlmm(imagePlmm);
    }

    /**
     * 删除美女图片对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteImagePlmmByIds(String ids)
    {
        return imagePlmmMapper.deleteImagePlmmByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除美女图片信息
     * 
     * @param id 美女图片ID
     * @return 结果
     */
    @Override
    public int deleteImagePlmmById(Long id)
    {
        return imagePlmmMapper.deleteImagePlmmById(id);
    }
}
