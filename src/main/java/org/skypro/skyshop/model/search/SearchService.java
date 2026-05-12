package org.skypro.skyshop.model.search;

import org.skypro.skyshop.model.service.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<SearchResult> search(String query) {
        return Stream.concat(
                        storageService.getAllProducts().stream(),
                        storageService.getAllArticles().stream()
                )
                .filter(searchable -> searchable.getSearchTerm().toLowerCase().contains(query.toLowerCase()))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}
