package com.example.shop_sv.modules.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRep {
    private Integer product_id;
    private String product_name;
    private Integer sku;
    private String description;
    private Integer unitPrice;
    private Integer stock_quantity;
    private Integer category_id;
    private List<String> imageUrls;
    private Boolean status;
    private String created_at;
    private String updated_at;
}
