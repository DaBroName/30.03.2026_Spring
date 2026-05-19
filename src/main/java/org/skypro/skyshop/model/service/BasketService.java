package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BasketService {

    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    // Добавление товара в корзину по id
    public void addProduct(UUID id) {
        Optional<Product> productOpt = storageService.getProductById(id);

        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + id + " not found in storage.");
        }
        productBasket.addProduct(id);
    }

    // Метод получения корзины
    public UserBasket getUserBasket() {

        java.util.Map<UUID, Integer> basketMap = productBasket.getProducts();

        java.util.List<BasketItem> basketItems = basketMap.entrySet().stream()

                .map(entry -> {
                    UUID id = entry.getKey();
                    int count = entry.getValue();

                    Product product = storageService.getProductById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found."));

                    return new BasketItem(product, count);
                })
                .collect(java.util.stream.Collectors.toList());

        return new UserBasket(basketItems);
    }
}
