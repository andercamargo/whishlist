package com.github.andercamargo.wishlist.data.repository.interfaces;


import com.github.andercamargo.wishlist.data.model.Wishlist;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IWishListRepository {
    Wishlist findByProductNameAndCustomer(String productName, Integer customerId);
    Wishlist findByProductIdAndCustomer(Integer productId, Integer customerId);
    Wishlist save(Wishlist wishlist);
    Wishlist update(Wishlist wishlist);
    long delete(Wishlist wishlist);
    Wishlist findAllByCostumer(Integer customerId);
}
