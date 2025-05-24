package ruanjian.order_dishes_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "dish_categories")
@IdClass(DishCategoryId.class)
public class DishCategory {
    @Id
    private Integer dishId;

    @Id
    private Integer categoryId;
}
