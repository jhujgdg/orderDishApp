package ruanjian.order_dishes_app.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Data
public class OrderCreateRequest {
    private Integer userId;
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private Integer dishId;
        private Integer quantity;
    }
}
