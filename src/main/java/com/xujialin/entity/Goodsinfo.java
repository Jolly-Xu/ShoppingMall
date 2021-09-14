package com.xujialin.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goodsinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品Id
     */
    private String id;

    /**
     * 商品名称
     */
    private String commodityname;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 打折价格
     */
    private BigDecimal discountprice;

    /**
     * 库存数量
     */
    private Integer stockcount;

    /**
     * 店铺名称
     */
    private String storeid;

    /**
     * 订单创建时间
     */
    private LocalDateTime createtime;

    /**
     * 订单更新时间
     */
    private LocalDateTime updatatime;

    /**
     * 逻辑删除
     */
    private Boolean islogicdelete;

    /**
     * 是否置顶
     */
    @TableField("isTop")
    private Boolean isTop;

    /**
     * 商品封面图片地址
     */
    private String commoditycoverimg;

    /**
     * 商品描述
     */
    private String commodityinfo;

    /**
     * 商品标题
     */
    private String commoditytitle;


}
