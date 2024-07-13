package com.example.shop_sv.modules.products.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResAdd {
    private String product_name;
    private String description;
    private Double unitPrice;
    private Integer stock_quantity;
    private List<String> images;
    private Integer category_id;

    @Override
    public String toString() {
        return "ProductResAdd{" +
                "product_name='" + product_name + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", stock_quantity=" + stock_quantity +
                ", images=" + images +
                ", category_id=" + category_id +
                '}';
    }
}
