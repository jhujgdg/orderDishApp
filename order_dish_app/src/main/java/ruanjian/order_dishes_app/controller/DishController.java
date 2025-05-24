package ruanjian.order_dishes_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ruanjian.order_dishes_app.entity.Dish;
import ruanjian.order_dishes_app.entity.DishReview;
import ruanjian.order_dishes_app.service.DishService;
import ruanjian.order_dishes_app.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private ReviewService reviewService;
    // 获取所有可用菜品
    @GetMapping
    public List<Dish> getAllDishes() {
        return dishService.getAllAvailableDishes();
    }

    // 通过关键词模糊搜索菜品
    @GetMapping("/search")
    public List<Dish> searchDishes(@RequestParam String keyword) {
        return dishService.searchDishes(keyword);
    }

    // 获取单个菜品详情
    @GetMapping("/{id}")
    public Dish getDishById(@PathVariable Integer id) {
        return dishService.getDishById(id)
                .orElseThrow(() -> new RuntimeException("未找到该菜品"));
    }

    // 获取推荐菜品（评分最高的前 N 个）
    @GetMapping("/recommend")
    public List<Dish> getTopRatedDishes(@RequestParam(defaultValue = "5") int limit) {
        return dishService.getTopRatedDishes(limit);
    }

    // 根据分类 ID 获取菜品分页列表
    @GetMapping("/category/{categoryId}")
    public Page<Dish> getDishesByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return dishService.getDishesByCategory(categoryId, page, size);
    }

    // 获取某个菜品的所有评论

    @GetMapping("/{dishId}/reviews")
    public List<DishReview> getReviewsByDishId(@PathVariable Integer dishId) {
        return reviewService.getReviewsByDishId(dishId);
    }

}
