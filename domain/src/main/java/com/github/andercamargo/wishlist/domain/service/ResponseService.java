package com.github.andercamargo.wishlist.domain.service;

import com.github.andercamargo.wishlist.domain.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class ResponseService <T>{
    public ResponseDTO covertResponse (int status, String message, T data) {
        return new ResponseDTO<T>(status, message, data);
    }
}
