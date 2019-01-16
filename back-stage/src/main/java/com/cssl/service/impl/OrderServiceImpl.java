package com.cssl.service.impl;

import cn.yiyuangou.pojo.Goods;
import cn.yiyuangou.pojo.Order;
import cn.yiyuangou.pojo.Order_details;
import com.cssl.dao.OrderDao;
import com.cssl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shksart
 * @date 2019/01/11 -  15:50
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    @Override
    public List<Order> seleOrders(String status) {
        return orderDao.seleOrders(status);
    }

    @Override
    public List<Goods> seleOrderdetailsWhereOrderId(String orderid) {
        return orderDao.seleOrderdetailsWhereOrderId(orderid);
    }


}
