package com.github.andercamargo.wishlist.app.controller;

import com.github.andercamargo.wishlist.domain.dto.WishlistDTO;
import com.github.andercamargo.wishlist.domain.service.WishListServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/v1/api")
@Validated
@Tag(name = "Wishlist")
public class WishlistController {

    private final WishListServiceImpl wishListService;

    @Autowired
    public WishlistController(WishListServiceImpl wishListService) {
        this.wishListService = wishListService;
    }


    @GetMapping("list/{customerId}")
    @Operation(summary = "List all wishlist")
    public ResponseEntity listAll(@Valid @PathVariable("customerId")
                                   @NotBlank(message = "customerId deve ser informado")
                                   @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="customerId deve ser um valor numerico positivo")
                                  String customerId){
        return wishListService.listAll(Integer.parseInt(customerId));
    }

    @PostMapping("add")
    @Operation(summary = "Add product in wishlist")
    public ResponseEntity add(@Valid @RequestBody WishlistDTO wishlistDTO) {
        return wishListService.add(wishlistDTO);
    }

    @GetMapping("find")
    @Operation(summary = "Find product in wishlist")
    public ResponseEntity find(@Valid
                                   @Pattern(regexp = "[0-9]+" ,message="productId  deve ser um valor numerico positivo")
                                   @RequestParam(name = "productId")
                                   String productId,
                               @NotBlank(message = "customerId deve ser informado")
                               @Pattern(regexp = "[0-9]+",message="customerId deve ser um valor numerico positivo")
                               @RequestParam(name = "customerId") String customerId) {
        return wishListService.find(Integer.parseInt(productId), Integer.parseInt(customerId));
    }


    @DeleteMapping("remove")
    @Operation(summary = "Find product in wishlist")
    public ResponseEntity delete(
            @Valid
            @Pattern(regexp = "[0-9]+" ,message="productId  deve ser um valor numerico positivo")
            @RequestParam(name = "productId")
            String productId,
            @NotBlank(message = "customerId deve ser informado")
            @Pattern(regexp = "[0-9]+",message="customerId deve ser um valor numerico positivo")
            @RequestParam(name = "customerId") String customerId) {
        return  wishListService.remove(Integer.parseInt(productId), Integer.parseInt(customerId));
    }
}
