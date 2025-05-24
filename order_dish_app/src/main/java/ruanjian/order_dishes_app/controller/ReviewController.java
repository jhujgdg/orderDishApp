package ruanjian.order_dishes_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruanjian.order_dishes_app.entity.DishReview;
import ruanjian.order_dishes_app.service.ReviewService;
import ruanjian.order_dishes_app.service.UserService;
import ruanjian.order_dishes_app.util.TokenUtils;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestHeader("Authorization") String authorization,
                                       @RequestBody DishReview review) {
        Integer userId = TokenUtils.extractUserId(authorization, userService);
        review.setUserId(userId);
        // 先校验是否可以评论
        boolean canReview = reviewService.canReview(review.getUserId(), review.getDishId(), review.getOrderId());
        if (!canReview) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("您已经评论过该菜品，无法重复评论！");
        }

        // 设置评论时间
        review.setReviewDate(LocalDateTime.now());

        // 添加评论
        DishReview saved = reviewService.addReview(review);
        return ResponseEntity.ok(saved);
    }
}

