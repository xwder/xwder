package com.xwder.app.modules.user.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * QQ用户信息表
 *
 * @Author xwder
 * @Date 2020-11-07 22:48:55
 */
@Entity
@Table(name = "app_user_qq")
@EntityListeners(AuditingEntityListener.class)
public class UserQQ implements Serializable {

    private static final long serialVersionUID = 6492830532271738603L;

    /**
     * id 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * QQ用户信息openID
     */
    @Column(name = "open_id")
    private String openId;

    @Column(name = "ret")
    private Long ret;

    @Column(name = "msg")
    private String msg;

    @Column(name = "is_lost")
    private Long isLost;

    @Column(name = "nickname")
    private String nickname;

    /**
     * 性别
     */
    @Column(name = "gender")
    private String gender;

    /**
     * 1-男
     */
    @Column(name = "gender_type")
    private Integer genderType;

    /**
     * 城市
     */
    @Column(name = "city")
    private String city;

    @Column(name = "year")
    private String year;

    @Column(name = "constellation")
    private String constellation;

    @Column(name = "figureurl")
    private String figureurl;

    @Column(name = "figureurl_1")
    private String figureurl1;

    @Column(name = "figureurl_2")
    private String figureurl2;

    @Column(name = "figureurl_qq_1")
    private String figureurlQq1;

    @Column(name = "figureurl_qq_2")
    private String figureurlQq2;

    @Column(name = "figureurl_qq")
    private String figureurlQq;

    @Column(name = "figureurl_type")
    private String figureurlType;

    @Column(name = "is_yellow_vip")
    private String isYellowVip;

    @Column(name = "vip")
    private String vip;

    @Column(name = "yellow_vip_level")
    private String yellowVipLevel;

    @Column(name = "level")
    private String level;

    @Column(name = "is_yellow_year_vip")
    private String isYellowYearVip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    public Long getRet() {
        return ret;
    }

    public void setRet(Long ret) {
        this.ret = ret;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public Long getIsLost() {
        return isLost;
    }

    public void setIsLost(Long isLost) {
        this.isLost = isLost;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public Integer getGenderType() {
        return genderType;
    }

    public void setGenderType(Integer genderType) {
        this.genderType = genderType;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }


    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }


    public String getFigureurl1() {
        return figureurl1;
    }

    public void setFigureurl1(String figureurl1) {
        this.figureurl1 = figureurl1;
    }


    public String getFigureurl2() {
        return figureurl2;
    }

    public void setFigureurl2(String figureurl2) {
        this.figureurl2 = figureurl2;
    }


    public String getFigureurlQq1() {
        return figureurlQq1;
    }

    public void setFigureurlQq1(String figureurlQq1) {
        this.figureurlQq1 = figureurlQq1;
    }


    public String getFigureurlQq2() {
        return figureurlQq2;
    }

    public void setFigureurlQq2(String figureurlQq2) {
        this.figureurlQq2 = figureurlQq2;
    }


    public String getFigureurlQq() {
        return figureurlQq;
    }

    public void setFigureurlQq(String figureurlQq) {
        this.figureurlQq = figureurlQq;
    }


    public String getFigureurlType() {
        return figureurlType;
    }

    public void setFigureurlType(String figureurlType) {
        this.figureurlType = figureurlType;
    }


    public String getIsYellowVip() {
        return isYellowVip;
    }

    public void setIsYellowVip(String isYellowVip) {
        this.isYellowVip = isYellowVip;
    }


    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }


    public String getYellowVipLevel() {
        return yellowVipLevel;
    }

    public void setYellowVipLevel(String yellowVipLevel) {
        this.yellowVipLevel = yellowVipLevel;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    public String getIsYellowYearVip() {
        return isYellowYearVip;
    }

    public void setIsYellowYearVip(String isYellowYearVip) {
        this.isYellowYearVip = isYellowYearVip;
    }

}
