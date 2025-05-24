package ruanjian.order_dishes_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruanjian.order_dishes_app.entity.DishReview;
import ruanjian.order_dishes_app.repository.DishReviewRepository;
import ruanjian.order_dishes_app.service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private DishReviewRepository dishReviewRepository;

    @Override
    public boolean canReview(Integer userId, Integer dishId, Integer orderId) {
        return dishReviewRepository
                .findByUserIdAndDishIdAndOrderId(userId, dishId, orderId)
                .isEmpty(); // 没有就可以评论
    }

    @Override
    public DishReview addReview(DishReview review) {
        return dishReviewRepository.save(review);
    }

    @Override
    public List<DishReview> getReviewsByDishId(Integer dishId) {
        return dishReviewRepository.findByDishId(dishId);
    }
}
