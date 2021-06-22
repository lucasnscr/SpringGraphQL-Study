package com.example.springgraphqlstudy.exception;

public class PostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String id) {
		super("Post: " + id + " was not found.");
	}
}
