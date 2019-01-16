package com.cssl.service;

import cn.yiyuangou.pojo.Goods;
import cn.yiyuangou.pojo.Order;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * @author shksart
 * @date 2019/01/11 -  15:49
 */
public interface OrderService {

    //订单展示(条件为订单状态)
    List<Order> seleOrders(@Param("status") String status);

    //根据订单编号连表查询商品
    List<Goods> seleOrderdetailsWhereOrderId(@Param("orderid") String orderid);

}
