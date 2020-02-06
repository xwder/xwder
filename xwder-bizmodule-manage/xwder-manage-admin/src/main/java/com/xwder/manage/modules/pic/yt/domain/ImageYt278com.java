package com.xwder.manage.modules.pic.yt.domain;

import com.xwder.manage.common.annotation.Excel;
import com.xwder.manage.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 妹子图图对象 image_yt278com
 *
 * @author xwder
 * @date 2020-02-07
 */
public class ImageYt278com extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 类别 */
    @Excel(name = "类别")
    private String albumType;

    /** 描述 */
    @Excel(name = "描述")
    private String albumName;

    /** 网站源地址 */
    private String albumSourceUrl;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long views;

    /** 网站源地址 */
    private String sourceImageUrls;

    /** 下载地址 */
    private String downImageUrls;

    /** 封面 */
    @Excel(name = "封面")
    private String albumImageUrl;

    /** 封面 */
    private String albumImageDownUrl;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gmtCreate;

    /** 修改时间 */
    private Date gmtModified;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAlbumType(String albumType)
    {
        this.albumType = albumType;
    }

    public String getAlbumType()
    {
        return albumType;
    }
    public void setAlbumName(String albumName)
    {
        this.albumName = albumName;
    }

    public String getAlbumName()
    {
        return albumName;
    }
    public void setAlbumSourceUrl(String albumSourceUrl)
    {
        this.albumSourceUrl = albumSourceUrl;
    }

    public String getAlbumSourceUrl()
    {
        return albumSourceUrl;
    }
    public void setViews(Long views)
    {
        this.views = views;
    }

    public Long getViews()
    {
        return views;
    }
    public void setSourceImageUrls(String sourceImageUrls)
    {
        this.sourceImageUrls = sourceImageUrls;
    }

    public String getSourceImageUrls()
    {
        return sourceImageUrls;
    }
    public void setDownImageUrls(String downImageUrls)
    {
        this.downImageUrls = downImageUrls;
    }

    public String getDownImageUrls()
    {
        return downImageUrls;
    }
    public void setAlbumImageUrl(String albumImageUrl)
    {
        this.albumImageUrl = albumImageUrl;
    }

    public String getAlbumImageUrl()
    {
        return albumImageUrl;
    }
    public void setAlbumImageDownUrl(String albumImageDownUrl)
    {
        this.albumImageDownUrl = albumImageDownUrl;
    }

    public String getAlbumImageDownUrl()
    {
        return albumImageDownUrl;
    }
    public void setGmtCreate(Date gmtCreate)
    {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate()
    {
        return gmtCreate;
    }
    public void setGmtModified(Date gmtModified)
    {
        this.gmtModified = gmtModified;
    }

    public Date getGmtModified()
    {
        return gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("albumType", getAlbumType())
                .append("albumName", getAlbumName())
                .append("albumSourceUrl", getAlbumSourceUrl())
                .append("views", getViews())
                .append("sourceImageUrls", getSourceImageUrls())
                .append("downImageUrls", getDownImageUrls())
                .append("albumImageUrl", getAlbumImageUrl())
                .append("albumImageDownUrl", getAlbumImageDownUrl())
                .append("gmtCreate", getGmtCreate())
                .append("gmtModified", getGmtModified())
                .toString();
    }
}