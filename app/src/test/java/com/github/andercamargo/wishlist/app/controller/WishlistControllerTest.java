package com.github.andercamargo.wishlist.app.controller;


import com.github.andercamargo.wishlist.domain.dto.WishlistDTO;
import com.github.andercamargo.wishlist.domain.service.WishListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishlistControllerTest {

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private WishListServiceImpl wishListService;

    private String customerId;
    private String productId;


    @BeforeEach
    public  void loadDefault(){
        customerId = "1";
        productId = "1";
    }

    @Test
    void shouldListAllWishlists() {
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        when(wishListService.listAll(anyInt())).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = wishlistController.listAll(customerId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldAddProductToWishlist() {
        WishlistDTO wishlistDTO = new WishlistDTO(null, null);
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(HttpStatus.CREATED);
        when(wishListService.add(any(WishlistDTO.class))).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = wishlistController.add(wishlistDTO);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldFindProductInWishlist() {
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        when(wishListService.find(anyInt(), anyInt())).thenReturn(expectedResponse);

        ResponseEntity<Object> actualResponse = wishlistController.find(productId, customerId);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void shouldRemoveProductFromWishlist() {
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(wishListService.remove(anyInt(), anyInt())).thenReturn(expectedResponse);

        ResponseEntity actualResponse = wishlistController.delete(productId, customerId);

        assertEquals(expectedResponse, actualResponse);
    }
}