package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    private final int productPrice;

    public SimpleProduct(String productName, UUID uuid, int productPrice) {
        // передаем данные в конструктор родителя
        super(productName,  uuid);

        if (productPrice <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть больше 0");
        }
        this.productPrice = productPrice;
    }

    // Класс предоставляет реализацию для методов, объявленных в родительском классе
    /*
    getPrice(): возвращает сохранённую цену товара
    isSpecial(): возвращает false, так как «простые» товары не имеют специальных признаков
     */

    @Override
    public int getPrice() {
        return productPrice;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public String toString() {
        return getProductName() + ": " + getPrice();
    }
}