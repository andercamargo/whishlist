package com.github.andercamargo.wishlist.domain.dto;

import com.github.andercamargo.wishlist.data.model.Customer;
import com.github.andercamargo.wishlist.data.model.Product;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class ProductDTO  {
    @NotEmpty(message = "id não pode ser vazio")
    @Digits(integer=10, fraction=0, message = "id inválido precisa ser um valor numérico")
    private String id;

    @NotEmpty(message = "name não pode ser vazio")
    private String name;

    @NotEmpty(message = "brand não pode ser vazio")
    private String brand;

    @NotEmpty(message = "category não pode ser vazio")
    private String category;

    @Digits(fraction = 2, integer = 3, message = "value deve ser um valor numerico maximo 3 centenas e 2 casas decimais")
    private Double value;


    public Product convert(ProductDTO productDTO){
        return new Product(Integer.parseInt(productDTO.getId()),
                        productDTO.getName(),
                        productDTO.getBrand(),
                        productDTO.getCategory(),
                        productDTO.getValue());

    }

}