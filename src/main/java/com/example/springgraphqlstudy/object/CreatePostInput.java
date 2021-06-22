package com.example.springgraphqlstudy.object;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CreatePostInput {
    @NotEmpty
    @Length(min = 5, max = 100)
    String title;

    @NotEmpty
    @Length(min = 5, max = 1000)
    String content;
}