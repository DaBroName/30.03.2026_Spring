package org.skypro.skyshop.model.basket;

import java.util.List;

public class UserBasket {
    private final List<BasketItem> items;
    private final int total;

    public UserBasket(List<BasketItem> items) {
        if (items == null) {
            this.items = List.of();
            this.total = 0;
        } else {
            // Создаем неизменяемую копию списка
            this.items = List.copyOf(items);
            // Подсчет общей стоимости с помощью Stream API
            this.total = items.stream()
                    .mapToInt(item -> item.getProduct().getPrice() * item.getCount())
                    .sum();
        }
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public int getTotal() {
        return total;
    }
}
