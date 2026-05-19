package org.skypro.skyshop.model.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage = new HashMap<>();
    private final Map<UUID, Article> articleStorage = new HashMap<>();

    public StorageService() {
        addData();
    }

    private void addData() {
        Product coffee = new SimpleProduct("Кофе", UUID.randomUUID(), 990); // Обычный товар
        Product tea = new SimpleProduct("Чай", UUID.randomUUID(), 470); // Обычный товар
        Product bread = new SimpleProduct("Хлеб", UUID.randomUUID(), 100); // Обычный товар
        Product water = new DiscountedProduct("Вода", UUID.randomUUID(), 170, 15); // Товар со скидкой
        Product beer = new DiscountedProduct("Пиво", UUID.randomUUID(), 320, 10); // Товар со скидкой
        Product milk = new FixPriceProduct("Молоко", UUID.randomUUID()); // Товар с фиксированной ценой. 50

        productStorage.put(coffee.getId(), coffee);
        productStorage.put(tea.getId(), tea);
        productStorage.put(bread.getId(), bread);
        productStorage.put(water.getId(), water);
        productStorage.put(beer.getId(), beer);
        productStorage.put(milk.getId(), milk);

        // Добавляем статьи
        Article howToBuy = new Article("Как купить", "Инструкция по покупке ", UUID.randomUUID());
        Article aboutUs = new Article("О нас", "Покупай у нас!", UUID.randomUUID());

        articleStorage.put(howToBuy.getId(), howToBuy);
        articleStorage.put(aboutUs.getId(), aboutUs);
    }

    // Метод, возвращающий коллекцию всех продуктов
    public Collection<Product> getAllProducts() {
        return productStorage.values();
    }

    // Метод, возвращающий коллекцию всех статей
    public Collection<Article> getAllArticles() {
        return articleStorage.values();
    }

    // Метод, возвращающий общую коллекцию всех объектов, доступных для поиска
    public Collection<Searchable> getSearchables() {
        Collection<Searchable> searchables = new ArrayList<>();
        searchables.addAll(productStorage.values());
        searchables.addAll(articleStorage.values());
        return searchables;
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productStorage.get(id));
    }


}
