package com.example.springgraphqlstudy.fetcher;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.example.springgraphqlstudy.domain.Author;
import com.example.springgraphqlstudy.domain.Comment;
import com.example.springgraphqlstudy.domain.Post;
import com.example.springgraphqlstudy.exception.AuthorNotFoundException;
import com.example.springgraphqlstudy.exception.PostNotFoundException;
import com.example.springgraphqlstudy.object.CommentInput;
import com.example.springgraphqlstudy.object.CreatePostInput;
import com.example.springgraphqlstudy.service.AuthorService;
import com.example.springgraphqlstudy.service.PostService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DgsComponent
@RequiredArgsConstructor
@Validated
public class PostsDataFetcher {
    private final PostService postService;
    private final AuthorService authorService;

    @DgsQuery
    public Flux<Post> allPosts() {
        return this.postService.getAllPosts();
    }

    @DgsQuery
    public Mono<Post> postById(@InputArgument String postId) {
        return this.postService.getPostById(postId)
                .switchIfEmpty(Mono.error(new PostNotFoundException(postId)));
    }

    @DgsData(parentType = "Post", field = "author")
    public Mono<Author> author(DgsDataFetchingEnvironment dfe) {
        Post post = dfe.getSource();
        String authorId = post.getAuthorId();
        return this.authorService.getAuthorById(authorId)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException(authorId)));
    }

    @DgsMutation
    public Mono<Post> createPost(@InputArgument("createPostInput") @Valid CreatePostInput input) {
        return this.postService.createPost(input);
    }

    @DgsMutation
    public Mono<Comment> addComment(@InputArgument("commentInput") @Valid CommentInput input) {
        Mono<Comment> comment = this.postService.addComment(input);
        return comment;
    }
}
