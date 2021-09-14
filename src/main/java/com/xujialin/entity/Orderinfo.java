package com.xujialin.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Orderinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    private String orderid;

    /**
     * 购买商品id
     */
    private String commodityid;

    /**
     * 购买价格
     */
    @TableField("Purchaseprice")
    private BigDecimal Purchaseprice;

    /**
     * 订单创建时间
     */
    private LocalDateTime createtime;

    /**
     * 订单修改时间
     */
    private LocalDateTime updatatime;

    /**
     * 购买时间
     */
    @TableField("Purchasetime")
    private LocalDateTime Purchasetime;

    /**
     * 订单状态0-购买失败 1-未付款  2-付款成功
     */
    @TableField("Orderstatus")
    private Integer Orderstatus;

    /**
     * 购买人id
     */
    private String purchaserid;

    /**
     * 发货地址
     */
    @TableField("Shippingaddress")
    private String Shippingaddress;

    /**
     * 收货地址
     */
    @TableField("Receivingaddress")
    private String Receivingaddress;

    /**
     * 快递种类
     */
    @TableField("Expresstype")
    private String Expresstype;

    /**
     * 快递id
     */
    @TableField("Expressid")
    private String Expressid;

    /**
     * 逻辑删除
     */
    private Boolean islogicdelete;


}
