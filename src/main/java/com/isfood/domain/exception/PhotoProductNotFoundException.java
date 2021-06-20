package com.isfood.domain.exception;

public class PhotoProductNotFoundException extends EntityNotFoundException {
    public PhotoProductNotFoundException(Long restaurantId, Long productId) {
        this(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d",
                productId, restaurantId));
    }

    public PhotoProductNotFoundException(String message) {
        super(message);
    }
}
