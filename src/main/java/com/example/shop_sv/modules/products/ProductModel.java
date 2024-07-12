package com.example.shop_sv.modules.products;

import com.example.shop_sv.modules.categories.CategoryModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id;
    // tên sản phẩm
    @Column(name = "product_name", nullable = false, unique = true)
    private String product_name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer unitPrice;

    private Integer stock_quantity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<ImageProduct> imageProducts;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private CategoryModel category;

    @Column(name = "status", columnDefinition = "BIT DEFAULT 1")
    private Boolean status;

    @Temporal(TemporalType.DATE)
    private String created_at;

    @Temporal(TemporalType.DATE)
    private String updated_at;


    public Integer generateSku() {
        return (int) (Math.random() * 1000000);
    }

}
