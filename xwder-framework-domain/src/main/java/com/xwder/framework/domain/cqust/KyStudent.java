package com.xwder.framework.domain.cqust;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: xwder
 * @Date: 2019/7/10 21:18
 * @Description:
 */
@Data
@Entity
@Table(name = "ky_student") // 指定关联的数据库的表名
public class KyStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String studentNo;

    private String studentName;

    private String nationId;

    private String sex;

    /**
     * 政治面貌
     */
    private String massIdentity;

    private String identityNo;

    private Date birthday;

    private String collgeNo;

    private String majorNo;

    private Date enrollmentTime;

    private String grade;

    /**
     * 学制 四年
     */
    private String educationSystem;

    private String status;

}
