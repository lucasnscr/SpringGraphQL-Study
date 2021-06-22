package com.example.springgraphqlstudy.object;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CommentInput {
	
	
    @NotEmpty
    @Length(min = 5, max = 50)
    String content;

    @NotEmpty
    String postId;
}