package com.example.springgraphqlstudy.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.springgraphqlstudy.domain.Comment;
import com.example.springgraphqlstudy.domain.Post;
import com.example.springgraphqlstudy.exception.PostNotFoundException;
import com.example.springgraphqlstudy.object.CommentInput;
import com.example.springgraphqlstudy.object.CreatePostInput;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {
    static Map<String, Post> STORE = new HashMap<>();

    public void init(List<Post> data) {
        STORE.clear();
        data.forEach(d -> STORE.put(d.getId(), d));
    }

    public Flux<Post> getAllPosts() {
        return Flux.fromIterable(STORE.values());
    }

    public Mono<Post> getPostById(String id) {
        return Mono.justOrEmpty(STORE.get(id));
    }

    Flux<Post> getPostsByAuthorId(String id) {
        return Flux.fromStream(STORE.values().stream().filter(p -> p.getAuthorId().equals(id)));
    }

    public Mono<Post> createPost(CreatePostInput postInput) {
        var data = Post.builder().id(UUID.randomUUID().toString())
                .title(postInput.getTitle())
                .content(postInput.getContent())
                .build();
        STORE.put(data.getId(), data);
        return Mono.just(data);
    }

    public Mono<Comment> addComment(CommentInput commentInput) {
        String postId = commentInput.getPostId();
        var p = STORE.get(postId);

        if (p != null) {
            var comment = Comment.builder()
                    .id(UUID.randomUUID().toString())
                    .postId(postId)
                    .content(commentInput.getContent())
                    .build();
            p.getComments().add(comment);
            return Mono.just(comment);
        } else {
            return Mono.error(new PostNotFoundException(postId));
        }
    }
}