package com.github.andercamargo.wishlist.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO<T> {
    private int code;
    private String message;
    private T data;
}
