package com.xwder.manage.modules.lolmz.mapper;

import com.xwder.manage.modules.lolmz.domain.LolmzPic;
import java.util.List;

/**
 * 英雄联盟Mapper接口
 * 
 * @author xwder
 * @date 2020-02-03
 */
public interface LolmzPicMapper 
{
    /**
     * 查询英雄联盟
     * 
     * @param id 英雄联盟ID
     * @return 英雄联盟
     */
    public LolmzPic selectLolmzPicById(Long id);

    /**
     * 查询英雄联盟列表
     * 
     * @param lolmzPic 英雄联盟
     * @return 英雄联盟集合
     */
    public List<LolmzPic> selectLolmzPicList(LolmzPic lolmzPic);

    /**
     * 新增英雄联盟
     * 
     * @param lolmzPic 英雄联盟
     * @return 结果
     */
    public int insertLolmzPic(LolmzPic lolmzPic);

    /**
     * 修改英雄联盟
     * 
     * @param lolmzPic 英雄联盟
     * @return 结果
     */
    public int updateLolmzPic(LolmzPic lolmzPic);

    /**
     * 删除英雄联盟
     * 
     * @param id 英雄联盟ID
     * @return 结果
     */
    public int deleteLolmzPicById(Long id);

    /**
     * 批量删除英雄联盟
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteLolmzPicByIds(String[] ids);
}
