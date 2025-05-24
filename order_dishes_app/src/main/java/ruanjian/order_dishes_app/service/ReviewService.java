package ruanjian.order_dishes_app.service;

import ruanjian.order_dishes_app.entity.DishReview;

import java.util.List;

public interface ReviewService {
    boolean canReview(Integer userId, Integer dishId, Integer orderId);
    DishReview addReview(DishReview review);
    List<DishReview> getReviewsByDishId(Integer dishId);
}
