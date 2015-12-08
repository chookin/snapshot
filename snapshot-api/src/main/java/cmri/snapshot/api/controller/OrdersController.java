package cmri.snapshot.api.controller;

import cmri.snapshot.api.domain.ResponseMessage;
import cmri.snapshot.api.repository.OrderRepository;
import cmri.utils.lang.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuyin on 15/12/6.
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * 获取指定用户的所有订单
     */
    @RequestMapping(value = "userOrders/get", method = RequestMethod.POST)
    public ResponseMessage getUserOrders(long uid) {
        return new ResponseMessage()
                .set("orders", JsonHelper.toJson(orderRepository.findUser(uid)))
                ;
    }

    /**
     * 获取指定用户的订单总数
     */
    @RequestMapping(value = "userOrdersCount/get", method = RequestMethod.POST)
    public ResponseMessage getUserOrdersCount(long uid) {
        return new ResponseMessage()
                .set("count", String.valueOf(orderRepository.countUserOrders(uid)))
                ;
    }

    /**
     * 获取指定摄影师的所有订单
     */
    @RequestMapping(value = "grapherOrders/get", method = RequestMethod.POST)
    public ResponseMessage getGrapherOrders(long uid) {
        return new ResponseMessage()
                .set("orders", JsonHelper.toJson(orderRepository.findGrapher(uid)))
                ;
    }

    /**
     * 获取指定摄影师的订单总数
     */
    @RequestMapping(value = "grapherOrdersCount/get", method = RequestMethod.POST)
    public ResponseMessage getGrapherOrdersCount(long uid) {
        return new ResponseMessage()
                .set("count", String.valueOf(orderRepository.countGrapherOrders(uid)))
                ;
    }
}
