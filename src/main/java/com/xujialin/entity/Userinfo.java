package com.xujialin.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author XujialinDashuaige
 * @since 2021-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Userinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 权限id
     */
    private Integer authorityId;

    /**
     * 用户创建时间
     */
    private LocalDateTime createtime;

    /**
     * 用户更新时间
     */
    private LocalDateTime updatatime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 是否逻辑删除
     */
    private Boolean isLogicDelete;


}
