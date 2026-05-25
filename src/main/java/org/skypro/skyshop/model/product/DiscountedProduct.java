package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private final int basePrice; // базовая цена
    private final int discount; // процент скидки от 0 до 100

    public DiscountedProduct(String productName, UUID uuid, int basePrice, int discount) {
        super(productName, uuid);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Базовая цена должна быть строго больше 0. Передано: " + basePrice);
        }

        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Процент скидки должен быть в диапазоне от 0 до 100 включительно. " +
                    "Передано: " + discount);
        }

        this.basePrice = basePrice;
        this.discount = discount;
    }

    // Расчет итоговой цены
    @Override
    public int getPrice() {
        // итоговая цена = базовая цена - процент скидки
        return basePrice * (100 - discount) / 100;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getProductName() + " со скидкой: " + getPrice() + " (скидка " + discount + "%)";
    }
}
