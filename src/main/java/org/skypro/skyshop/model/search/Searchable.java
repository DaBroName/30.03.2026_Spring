package org.skypro.skyshop.model.search;

import java.util.UUID;

public interface Searchable {
    String getSearchTerm(); // Что ищем

    String getTypeContent(); // Тип контента

    String getName(); // Метод получения заголовка

    // метод, возвращающий UUID
    UUID getId();

    default String getStringRepresentation() { // Дефолтный метод для строкового представления
        return getName() + " — " + getTypeContent();
    }
}