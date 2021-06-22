package com.example.springgraphqlstudy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.springgraphqlstudy.domain.Author;

import reactor.core.publisher.Mono;

@Service
public class AuthorService {

    static Map<String, Author> STORE = new HashMap<>();

    public void init(List<Author> data) {
        STORE.clear();
        data.forEach(d -> STORE.put(d.getId(), d));
    }

    public Mono<Author> getAuthorById(String id) {
        return Mono.justOrEmpty(STORE.get(id));
    }
}
