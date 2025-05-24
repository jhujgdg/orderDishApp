package ruanjian.order_dishes_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruanjian.order_dishes_app.dto.OrderCreateRequest;
import ruanjian.order_dishes_app.entity.Order;
import ruanjian.order_dishes_app.service.OrderService;
import ruanjian.order_dishes_app.service.UserService;
import ruanjian.order_dishes_app.util.TokenUtils;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Order createOrder(@RequestHeader("Authorization") String authorization,
                             @RequestBody OrderCreateRequest request) {
        Integer userId = TokenUtils.extractUserId(authorization, userService);
        request.setUserId(userId);
        return orderService.createOrder(request);
    }


    @GetMapping("/list")
    @ResponseBody
    public List<Order> getOrdersByStatus(@RequestParam Order.OrderStatus status, @RequestHeader("Authorization") String authorization) {
        Integer userId = TokenUtils.extractUserId(authorization, userService);
        return orderService.getOrdersByUserIdAndStatus(userId, status);
    }


    @PutMapping("/complete/{orderId}")
    public ResponseEntity<String> completeOrder(@PathVariable Integer orderId) {
        orderService.completeOrder(orderId);
        return ResponseEntity.ok("订单已完成");
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("订单已取消");
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetail(@PathVariable Integer orderId,
                                                @RequestHeader("Authorization") String authorization) {
        Integer userId = TokenUtils.extractUserId(authorization, userService);
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

}
