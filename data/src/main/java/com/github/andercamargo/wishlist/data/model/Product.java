package com.github.andercamargo.wishlist.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Product  {
    private Integer id;
    private String name;
    private String brand;
    private String category;
    private Double value;
}
