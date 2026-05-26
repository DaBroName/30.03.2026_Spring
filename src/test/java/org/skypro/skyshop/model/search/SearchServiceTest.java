package org.skypro.skyshop.model.search;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.service.StorageService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    private Product testProduct;
    private Article testArticle;

    @BeforeEach
    void setUp() {
        // Инициализируем демонстрационные объекты перед каждым тестом
        testProduct = new SimpleProduct("Apple MacBook", UUID.randomUUID(), 15_000_000);
        testArticle = new Article("Как выбрать макбук", "Руководство по покупке ноутбука для работы", UUID.randomUUID());
    }

    @Test
    @Disabled("Поиск в случае полного отсутствия объектов в StorageService")
    void testSearchWhenStorageIsEmpty() {
        when(storageService.getAllProducts()).thenReturn(Collections.emptyList());
        when(storageService.getAllArticles()).thenReturn(Collections.emptyList());

        List<SearchResult> results = searchService.search("ноутбук");

        assertTrue(results.isEmpty(), "Результат поиска должен быть пустым, если хранилище пусто");

        verify(storageService, times(1)).getAllProducts();
        verify(storageService, times(1)).getAllArticles();
    }

    @Test
    @DisplayName("Поиск в случае, если объекты в StorageService есть, но нет подходящего")
    void testSearchWhenObjectsExistButNoMatch() {
        // Настраиваем мок: объекты есть в хранилище
        when(storageService.getAllProducts()).thenReturn(List.of(testProduct));
        when(storageService.getAllArticles()).thenReturn(List.of(testArticle));

        // Ищем строку, которой заведомо нет ни в продуктах, ни в статьях
        List<SearchResult> results = searchService.search("Смартфон");

        assertTrue(results.isEmpty(), "Результат поиска должен быть пустым, если нет совпадений по шаблону");
    }

    @Test
    @DisplayName("Поиск, когда есть подходящий объект (регистронезависимый поиск)")
    void testSearchWhenMatchExistsCaseInsensitive() {
        when(storageService.getAllProducts()).thenReturn(List.of(testProduct));
        when(storageService.getAllArticles()).thenReturn(List.of(testArticle));

        // Запрос написан маленькими буквами "ноутбук", а в продукте "Ноутбук"
        List<SearchResult> results = searchService.search("ноутбук");

        assertEquals(2, results.size(), "Должно быть найдено ровно 2 объекта (продукт и статья)");

        // Проверяем корректность трансформации в SearchResult для первого элемента
        SearchResult productResult = results.stream()
                .filter(r -> r.getTypeContent().equals("PRODUCT"))
                .findFirst()
                .orElseThrow();

        assertEquals(testProduct.getId().toString(), productResult.getId());
        assertEquals(testProduct.getName(), productResult.getName());
    }
}
