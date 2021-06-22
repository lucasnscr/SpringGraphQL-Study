package com.example.springgraphqlstudy.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.springgraphqlstudy.exception.AuthorNotFoundException;
import com.example.springgraphqlstudy.exception.PostNotFoundException;
import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.TypedGraphQLError;

import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {
    private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

    @Override
    public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters handlerParameters) {
        Throwable exception = handlerParameters.getException();
        if (exception instanceof PostNotFoundException || exception instanceof AuthorNotFoundException) {
            Map<String, Object> debugInfo = new HashMap<>();

            GraphQLError graphqlError = TypedGraphQLError.newNotFoundBuilder()
                    .message(exception.getMessage())
                    .debugInfo(debugInfo)
                    .path(handlerParameters.getPath())
                    .build();
            return DataFetcherExceptionHandlerResult.newResult()
                    .error(graphqlError)
                    .build();
        } else {
            return defaultHandler.onException(handlerParameters);
        }
    }
}
