package ruanjian.order_dishes_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ruanjian.order_dishes_app.entity.Dish;
import ruanjian.order_dishes_app.entity.DishReview;
import ruanjian.order_dishes_app.repository.DishRepository;
import ruanjian.order_dishes_app.repository.DishReviewRepository;
import ruanjian.order_dishes_app.service.DishService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishReviewRepository dishReviewRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, DishReviewRepository dishReviewRepository) {
        this.dishRepository = dishRepository;
        this.dishReviewRepository = dishReviewRepository;
    }

    @Override
    public List<Dish> getAllAvailableDishes() {
        return dishRepository.findByIsAvailableTrue();
    }

    @Override
    public List<Dish> searchDishes(String keyword) {
        return dishRepository.findByNameContainingIgnoreCaseAndIsAvailableTrue(keyword);
    }

    @Override
    public Optional<Dish> getDishById(Integer id) {
        return dishRepository.findById(id);
    }

    @Override
    public List<DishReview> getReviewsForDish(Integer dishId) {
        return dishReviewRepository.findByDishId(dishId);
    }

    @Override
    public List<Dish> getTopRatedDishes(int limit) {
        List<Object[]> results = dishReviewRepository.findTopRatedDishIds(PageRequest.of(0, limit));
        if (results.isEmpty()) {
            // ⚠️ 没有评分数据时，默认取前 N 个上架菜品
            return dishRepository.findAll(PageRequest.of(0, limit)).getContent();
        }

        List<Integer> topDishIds = results.stream()
                .map(obj -> (Integer) obj[0])
                .collect(Collectors.toList());
        return dishRepository.findAllById(topDishIds);
    }

    @Override
    public Page<Dish> getDishesByCategory(Integer categoryId, int page, int size) {
        return dishRepository.findByCategoryId(categoryId, PageRequest.of(page, size));
    }


}
