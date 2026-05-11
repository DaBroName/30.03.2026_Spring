package org.skypro.skyshop.model.product;

import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final String productName;
    private final UUID id;

    // В конструкторе класса проверяем, что название товара не null и не состоит только из пробелов
    public Product(String productName, UUID id) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        this.productName = productName;
        this.id = id;
    }

    // Методы equals и hashCode реализуем с учётом только имени товара (productName)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productName, product.productName);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productName);
    }

    public String getProductName() {
        return productName;
    }

    // Класс Product определяет два обязательных метода для всех наследников
    /*
    getPrice() - возвращает стоимость товара
    isSpecial() - позволяет пометить товар как «специальный» (скидка или фикс. цена)
     */

    public abstract boolean isSpecial();

    public abstract int getPrice();

    // Реализация интерфейса Searchable
    /*
    Класс реализует методы, необходимые для поиска:
    getSearchTerm() — возвращает имя товара для поиска
    getTypeContent() — возвращает константу "PRODUCT", чтобы отличать товары от статей
    getName() — алиас для получения имени, требуемый интерфейсом
     */
    @Override
    public String getSearchTerm() {
        return getProductName();
    }

    @Override
    public String getTypeContent() {
        return "PRODUCT";
    }

    @Override
    public String getName() {
        return getProductName();
    }
}