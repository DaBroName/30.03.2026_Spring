package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final int FIX_PRICE = 500;

    public FixPriceProduct(String productName, UUID uuid) {
        super(productName, uuid); // Передаем только имя в родительский конструктор
    }

    @Override
    public int getPrice() {
        return(FIX_PRICE); // Всегда возвращаем значение константы
    }

    @Override
    public boolean isSpecial() {
        return true; // относится к данному классу
    }

    @Override
    public String toString() {
        return getProductName() + " с фиксированной ценой: " + getPrice();
    }
}
