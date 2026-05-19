package org.skypro.skyshop.model.controller;

import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.service.BasketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    // Метод добавления продукта в корзину по ID из URL
    @GetMapping("/basket/{id}")
    public String addProduct(@PathVariable("id") UUID id) {
        basketService.addProduct(id);
        return "*Продукт успешно добавлен*";
    }

    // Метод отображения корзины пользователя в формате JSON
    @GetMapping("/basket")
    public UserBasket getUserBasket() {
        return basketService.getUserBasket();
    }
}
