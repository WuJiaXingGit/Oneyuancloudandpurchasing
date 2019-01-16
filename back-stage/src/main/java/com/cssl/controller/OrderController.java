package com.cssl.controller;

import cn.yiyuangou.pojo.Goods;
import cn.yiyuangou.pojo.Order;
import cn.yiyuangou.pojo.Order_details;
import com.cssl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author shksart
 * @date 2019/01/11 -  15:51
 */
@Controller
@RequestMapping("/back-stage")
public class OrderController {

    @Qualifier("orderServiceImpl")
    @Autowired
    private OrderService orderService;

    @RequestMapping("/getOrder")
    @ResponseBody
    public ModelAndView seleOrders(String status, ModelAndView mv){
        System.out.println("进入订单查询控制器..");
        System.out.println("订单状态值为："+status);
        List<Order> orders= orderService.seleOrders(status);
        //遍历订单对象(取出每个订单id)
        for (Order order : orders) {
            System.out.println("每个订单的id为："+order.getOrder_details().getOrder_id());
            List<Goods> goodsList= orderService.seleOrderdetailsWhereOrderId(order.getOrder_details().getOrder_id());
            order.getOrder_details().setGoods(goodsList);
        }
        mv.addObject("orders",orders);
        return  mv;
    }


}
