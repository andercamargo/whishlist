package com.github.andercamargo.wishlist.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Wishlist {
    @JsonIgnore
    private ObjectId id;
    private List<Product> products;
    private Customer customer;

    public Wishlist(List<Product> products, Customer customer) {
        this.products = products;
        this.customer = customer;
    }
}
