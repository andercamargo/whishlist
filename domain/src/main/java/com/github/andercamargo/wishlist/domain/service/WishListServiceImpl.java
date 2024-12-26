package com.github.andercamargo.wishlist.domain.service;

import com.github.andercamargo.wishlist.domain.dto.WishlistDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl extends BaseWishlistServiceImpl {

    @Override
    public ResponseEntity listAll(Integer customerId) {
        return super.listAll(customerId);
    }

    @Override
    public ResponseEntity add(WishlistDTO wishlistDTO) {
       return super.add(wishlistDTO);
    }

    @Override
    public ResponseEntity find(Integer productId, Integer customerId) {
        return super.find(productId, customerId);
    }

    @Override
    public ResponseEntity remove(Integer productId, Integer customerId) {
        return super.remove(productId, customerId);
    }
}
