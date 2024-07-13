package com.example.shop_sv.modules.categories;

import com.example.shop_sv.modules.products.ProductModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="categories")
@Entity
@Builder
public class CategoryModel {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String iconUrl;
    private Boolean status = true;

    @OneToMany
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private List<ProductModel> products;

    @Override
    public String toString() {
        return "CategoryModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", status=" + status +
                '}';
    }
}
