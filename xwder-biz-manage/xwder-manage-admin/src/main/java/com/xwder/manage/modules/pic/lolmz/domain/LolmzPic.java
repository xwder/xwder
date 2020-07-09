package com.xwder.manage.modules.pic.lolmz.domain;

import com.xwder.manage.common.annotation.Excel;
import com.xwder.manage.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 英雄联盟对象 lolmz_pic
 * 
 * @author xwder
 * @date 2020-02-03
 */
public class LolmzPic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** null */
    private Long id;

    /** null */
    @Excel(name = "null")
    private Long pid;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String uid;

    /** 头像图片地址 */
    @Excel(name = "头像图片地址")
    private String logoImgUrl;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 游戏大区 */
    @Excel(name = "游戏大区")
    private String gameArea;

    /** 游戏名称 */
    @Excel(name = "游戏名称")
    private String gameUsername;

    /** 总的点数数量 */
    @Excel(name = "总的点数数量")
    private Long zanNum;

    /** 图片点赞数量 */
    @Excel(name = "图片点赞数量")
    private Long picNum;

    /** 图片网站地址 */
    @Excel(name = "图片网站地址")
    private String sourceImageUrl;

    /** 图片下载地址 */
    @Excel(name = "图片下载地址")
    private String downImageUrl;

    /** 图片描述 */
    @Excel(name = "图片描述")
    private String imageDesc;

    /** 图片时间 */
    @Excel(name = "图片时间")
    private String imageTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPid(Long pid) 
    {
        this.pid = pid;
    }

    public Long getPid() 
    {
        return pid;
    }
    public void setUid(String uid) 
    {
        this.uid = uid;
    }

    public String getUid() 
    {
        return uid;
    }
    public void setLogoImgUrl(String logoImgUrl) 
    {
        this.logoImgUrl = logoImgUrl;
    }

    public String getLogoImgUrl() 
    {
        return logoImgUrl;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setGameArea(String gameArea) 
    {
        this.gameArea = gameArea;
    }

    public String getGameArea() 
    {
        return gameArea;
    }
    public void setGameUsername(String gameUsername) 
    {
        this.gameUsername = gameUsername;
    }

    public String getGameUsername() 
    {
        return gameUsername;
    }
    public void setZanNum(Long zanNum) 
    {
        this.zanNum = zanNum;
    }

    public Long getZanNum() 
    {
        return zanNum;
    }
    public void setPicNum(Long picNum) 
    {
        this.picNum = picNum;
    }

    public Long getPicNum() 
    {
        return picNum;
    }
    public void setSourceImageUrl(String sourceImageUrl) 
    {
        this.sourceImageUrl = sourceImageUrl;
    }

    public String getSourceImageUrl() 
    {
        return sourceImageUrl;
    }
    public void setDownImageUrl(String downImageUrl) 
    {
        this.downImageUrl = downImageUrl;
    }

    public String getDownImageUrl() 
    {
        return downImageUrl;
    }
    public void setImageDesc(String imageDesc) 
    {
        this.imageDesc = imageDesc;
    }

    public String getImageDesc() 
    {
        return imageDesc;
    }
    public void setImageTime(String imageTime) 
    {
        this.imageTime = imageTime;
    }

    public String getImageTime() 
    {
        return imageTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pid", getPid())
            .append("uid", getUid())
            .append("logoImgUrl", getLogoImgUrl())
            .append("username", getUsername())
            .append("gameArea", getGameArea())
            .append("gameUsername", getGameUsername())
            .append("zanNum", getZanNum())
            .append("picNum", getPicNum())
            .append("sourceImageUrl", getSourceImageUrl())
            .append("downImageUrl", getDownImageUrl())
            .append("imageDesc", getImageDesc())
            .append("imageTime", getImageTime())
            .toString();
    }
}
