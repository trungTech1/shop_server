package com.example.shop_sv.modules.categories;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
