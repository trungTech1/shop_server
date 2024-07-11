package com.example.shop_sv.modules.address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "address")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean isDefault;

    private String title;

    private String detail;

    private Integer ProvinceID;

    private String ProvinceName;

    private Integer DistrictID;

    private String DistrictName;

    private String WardCode;

    private String WardName;

    private String phone;

    private String name;
}
