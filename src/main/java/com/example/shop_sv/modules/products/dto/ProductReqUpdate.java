package com.example.shop_sv.modules.products.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductReqUpdate {
    private Integer product_id;
    private String product_name;
    private String description;
    private List<String> imageUrls;
    private Double unitPrice;
    private Integer stock_quantity;
    private Integer category_id;
    private Boolean status;

    @Override
    public String toString() {
        return "ProductReqUpdate{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +

                ", description='" + description + '\'' +
                ", images=" + imageUrls +
                ", unitPrice=" + unitPrice +
                ", stock_quantity=" + stock_quantity +
                ", categoryId=" + category_id +
                ", status=" + status +
                '}';
    }
}
