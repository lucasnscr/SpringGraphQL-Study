package com.example.springgraphqlstudy.exception;

public class AuthorNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AuthorNotFoundException(String id) {
        super("Author: " + id + " was not found.");
    }
}
