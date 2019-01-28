package com.patrikturi.todoapp.errors;

public class TodoNotFoundException extends RuntimeException {

	public TodoNotFoundException(Long id) {
		super("Could not find Todo " + id);
	}
}
