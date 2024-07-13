package com.example.shop_sv.modules.products;

import com.example.shop_sv.modules.categories.CategoryModel;
import com.example.shop_sv.modules.imageProduct.ImageProductModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double price;
    private Integer stockQuantity;
    @ManyToOne
    @JsonManagedReference
    private CategoryModel category;

    private Boolean status = true;

    private Boolean isDeleted = false;

    private String createdDate;

    private String updatedDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private List<ImageProductModel> images;

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", status=" + status +
                ", isDeleted=" + isDeleted +
                ", images=" + images +
                '}';
    }
}

