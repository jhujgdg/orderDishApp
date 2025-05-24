package ruanjian.order_dishes_app.service;

import ruanjian.order_dishes_app.dto.OrderCreateRequest;
import ruanjian.order_dishes_app.entity.Order;
import ruanjian.order_dishes_app.entity.OrderItem;

import java.util.List;

public interface OrderService {

    List<Order> getOrdersByStatus(Integer userId, Order.OrderStatus status);
    Order createOrder(OrderCreateRequest request);
    List<Order> getOrdersByUserIdAndStatus(Integer userId, Order.OrderStatus status);
    void completeOrder(Integer orderId);
    void cancelOrder(Integer orderId);
    Order getOrderById(Integer orderId);


}