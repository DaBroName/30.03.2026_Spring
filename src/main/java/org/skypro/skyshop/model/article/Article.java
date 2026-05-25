package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public final class Article implements Searchable {
    private final String articleTitle;
    private final String articleText;

    private final UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        // Сравниваем только по названию статьи
        return Objects.equals(articleTitle, article.articleTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(articleTitle);
    }

    public Article(String articleTitle, String articleText, UUID id) {
        this.articleTitle = articleTitle;
        this.articleText = articleText;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Название статьи: " + articleTitle + "\n" +
                "Текст статьи: " + articleText;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return this.toString();
    }

    @Override
    @JsonIgnore
    public String getTypeContent() {
        return "ARTICLE";
    }

    @Override
    public String getName() {
        return articleTitle;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
