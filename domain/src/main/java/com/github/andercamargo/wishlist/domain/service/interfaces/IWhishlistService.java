package com.github.andercamargo.wishlist.domain.service.interfaces;

import com.github.andercamargo.wishlist.domain.dto.WishlistDTO;
import org.springframework.http.ResponseEntity;

public interface IWhishlistService<T> {
    ResponseEntity listAll(Integer customerId);
    ResponseEntity add(WishlistDTO wishlistDTO);
    ResponseEntity  find(Integer productId, Integer customerId);
    ResponseEntity remove(Integer productId, Integer customerId);
}
