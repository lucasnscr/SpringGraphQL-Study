package com.example.springgraphqlstudy.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Author {
	
	String id;
	String name;
	String email;
	List<Post> posts = new ArrayList<>();

}
