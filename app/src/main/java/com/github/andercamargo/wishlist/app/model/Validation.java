package com.github.andercamargo.wishlist.app.model;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Validation {
    private List<FieldError> fieldErrors = new ArrayList<>();

    public void addFieldError(String path, String field, String defaultMessage) {
        FieldError error = new FieldError(path, field, defaultMessage);
        fieldErrors.add(error);
    }
}
