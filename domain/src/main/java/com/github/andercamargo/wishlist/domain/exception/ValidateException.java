package com.github.andercamargo.wishlist.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidateException extends Exception{
    private String message;
}
