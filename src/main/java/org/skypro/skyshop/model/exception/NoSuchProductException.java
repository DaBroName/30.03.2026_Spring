package org.skypro.skyshop.model.exception;

import java.util.UUID;

public class NoSuchProductException extends RuntimeException {

    public NoSuchProductException(String message) {
        super(message);
    }

    public NoSuchProductException(UUID id) {
        super("Продукт с ID " + id + " не найден");
    }
}
