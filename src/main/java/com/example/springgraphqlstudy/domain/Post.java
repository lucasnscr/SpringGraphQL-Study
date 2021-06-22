package com.example.springgraphqlstudy.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Post {
    String id;
    String title;
    String content;

    @Builder.Default
    List<Comment> comments = new ArrayList<>();
    String authorId;
    Author author;
}
