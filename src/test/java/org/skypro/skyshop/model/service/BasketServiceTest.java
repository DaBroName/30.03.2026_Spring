package org.skypro.skyshop.model.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.exception.NoSuchProductException;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {
    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    private UUID existingProductId;
    private UUID nonExistingProductId;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        existingProductId = UUID.randomUUID();
        nonExistingProductId = UUID.randomUUID();
        testProduct = new SimpleProduct("Тестовый продукт", existingProductId, 10000);
    }

    @Test
    @DisplayName("Добавление несуществующего товара в корзину приводит к выбросу исключения")
    void testAddProductWhenProductDoesNotExistThrowsException() {
        // Настраиваем мок: возвращаем пустой Optional при поиске несуществующего ID
        when(storageService.getProductById(nonExistingProductId)).thenReturn(Optional.empty());

        // Проверяем, что выбрасывается именно NoSuchProductException
        assertThrows(NoSuchProductException.class, () -> {
            basketService.addProduct(nonExistingProductId);
        });

        // Убеждаемся, что метод добавления в саму корзину никогда не вызывался
        verify(productBasket, never()).addProduct(any());
    }

    @Test
    @DisplayName("Добавление существующего товара вызывает метод addProduct у мока ProductBasket")
    void testAddProductWhenProductExistsCallsBasket() {
        // Настраиваем мок: товар успешно находится в StorageService
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(testProduct));
        basketService.addProduct(existingProductId);

        // Проверяем, что у объекта productBasket вызвался метод addProduct ровно 1 раз
        verify(productBasket, times(1)).addProduct(existingProductId);
    }

    @Test
    @DisplayName("Метод getUserBasket возвращает пустую корзину, если ProductBasket пуст")
    void testGetUserBasketWhenBasketIsEmpty() {
        // Настраиваем мок: корзина возвращает пустую карту товаров
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());
        UserBasket userBasket = basketService.getUserBasket();

        // Проверяем состояние пустой корзины
        assertNotNull(userBasket);
        assertTrue(userBasket.getItems().isEmpty());
        assertEquals(0, userBasket.getTotal());
    }

    @Test
    @DisplayName("Метод getUserBasket возвращает подходящую корзину, если в ProductBasket есть товары")
    void testGetUserBasketWhenBasketHasItems() {
        int productCount = 3;
        // Возвращаем один товар в количестве 3 штук
        when(productBasket.getProducts()).thenReturn(Map.of(existingProductId, productCount));
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(testProduct));
        UserBasket userBasket = basketService.getUserBasket();

        // Проверяем, что корзина сформирована корректно
        assertNotNull(userBasket);
        assertEquals(1, userBasket.getItems().size());
        BasketItem basketItem = userBasket.getItems().get(0);
        assertEquals(testProduct, basketItem.getProduct());
        assertEquals(productCount, basketItem.getCount());

        // Ожидаемая сумма: 10000 * 3 = 3000
        int expectedTotal = testProduct.getPrice() * productCount;
        assertEquals(expectedTotal, userBasket.getTotal());
    }
}
