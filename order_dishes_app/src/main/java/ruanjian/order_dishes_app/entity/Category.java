package ruanjian.order_dishes_app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    private String name;

}
