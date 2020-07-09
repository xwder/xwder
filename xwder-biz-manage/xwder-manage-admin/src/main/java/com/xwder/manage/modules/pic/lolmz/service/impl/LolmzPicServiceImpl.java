package com.xwder.manage.modules.pic.lolmz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xwder.manage.modules.pic.lolmz.mapper.LolmzPicMapper;
import com.xwder.manage.modules.pic.lolmz.domain.LolmzPic;
import com.xwder.manage.modules.pic.lolmz.service.ILolmzPicService;
import com.xwder.manage.common.core.text.Convert;

/**
 * 英雄联盟Service业务层处理
 * 
 * @author xwder
 * @date 2020-02-03
 */
@Service
public class LolmzPicServiceImpl implements ILolmzPicService 
{
    @Autowired
    private LolmzPicMapper lolmzPicMapper;

    /**
     * 查询英雄联盟
     * 
     * @param id 英雄联盟ID
     * @return 英雄联盟
     */
    @Override
    public LolmzPic selectLolmzPicById(Long id)
    {
        return lolmzPicMapper.selectLolmzPicById(id);
    }

    /**
     * 查询英雄联盟列表
     * 
     * @param lolmzPic 英雄联盟
     * @return 英雄联盟
     */
    @Override
    public List<LolmzPic> selectLolmzPicList(LolmzPic lolmzPic)
    {
        return lolmzPicMapper.selectLolmzPicList(lolmzPic);
    }

    /**
     * 新增英雄联盟
     * 
     * @param lolmzPic 英雄联盟
     * @return 结果
     */
    @Override
    public int insertLolmzPic(LolmzPic lolmzPic)
    {
        return lolmzPicMapper.insertLolmzPic(lolmzPic);
    }

    /**
     * 修改英雄联盟
     * 
     * @param lolmzPic 英雄联盟
     * @return 结果
     */
    @Override
    public int updateLolmzPic(LolmzPic lolmzPic)
    {
        return lolmzPicMapper.updateLolmzPic(lolmzPic);
    }

    /**
     * 删除英雄联盟对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteLolmzPicByIds(String ids)
    {
        return lolmzPicMapper.deleteLolmzPicByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除英雄联盟信息
     * 
     * @param id 英雄联盟ID
     * @return 结果
     */
    @Override
    public int deleteLolmzPicById(Long id)
    {
        return lolmzPicMapper.deleteLolmzPicById(id);
    }
}
