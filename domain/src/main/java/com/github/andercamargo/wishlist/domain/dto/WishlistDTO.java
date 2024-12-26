package com.github.andercamargo.wishlist.domain.dto;

import com.github.andercamargo.wishlist.data.model.Customer;
import com.github.andercamargo.wishlist.data.model.Product;
import com.github.andercamargo.wishlist.data.model.Wishlist;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class WishlistDTO {
    @Valid private ProductDTO product;
    @Valid private CustomerDTO customer;


    public Wishlist convert(WishlistDTO wishlistDTO){
        var products = new LinkedList<Product>();

        products.add(wishlistDTO.getProduct().convert(wishlistDTO.getProduct()));

        return new Wishlist(products, wishlistDTO.getCustomer().convert(wishlistDTO.getCustomer()));
    }
}
