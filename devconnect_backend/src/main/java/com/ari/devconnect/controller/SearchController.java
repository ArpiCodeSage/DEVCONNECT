package com.ari.devconnect.controller;

import com.ari.devconnect.dto.SearchResponse;
import com.ari.devconnect.service.SearchService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public SearchResponse search(@RequestParam String q) {
        return searchService.search(q);
    }
}