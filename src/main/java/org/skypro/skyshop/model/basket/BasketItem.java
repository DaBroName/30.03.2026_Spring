package org.skypro.skyshop.model.basket;

import org.skypro.skyshop.model.product.Product;

public class BasketItem {
    private final Product product;
    private final int count;

    public BasketItem(Product product, int count) {
        if (product == null) {
            throw new IllegalArgumentException("Продукт не может быть null");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть больше 0");
        }
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }
}
