package com.github.andercamargo.wishlist.domain.dto;

import com.github.andercamargo.wishlist.data.model.Customer;
import com.github.andercamargo.wishlist.data.model.Product;
import com.github.andercamargo.wishlist.data.model.Wishlist;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedList;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Validated
public class CustomerDTO {
    @NotEmpty(message = "name não pode ser vazio")
    private String name;

    @NotEmpty(message = "id não pode ser vazio")
    @Min(value = 0, message = "valor minimo 0")
    @NotEmpty(message = "id não pode ser vazio")
    @Digits(integer=10, fraction=0, message = "id inválido precisa ser um valor numérico")
    private String id;


    public Customer convert(CustomerDTO customerDTO){
        return new Customer(Integer.parseInt(customerDTO.getId()), customerDTO.getName());
    }
}
