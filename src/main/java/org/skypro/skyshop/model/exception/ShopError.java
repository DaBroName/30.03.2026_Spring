package org.skypro.skyshop.model.exception;

public final class ShopError {
    // сообщение об ошибке
    private final String message;
    // код ошибки в виде строки
    private final String code;

    public ShopError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public String getCode() {
        return code;
    }
}
