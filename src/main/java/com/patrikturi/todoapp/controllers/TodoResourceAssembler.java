package com.patrikturi.todoapp.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.patrikturi.todoapp.data.entity.Todo;

@Component
class TodoResourceAssembler implements ResourceAssembler<Todo, Resource<Todo>> {

	@Override
	public Resource<Todo> toResource(Todo todo) {
		//@formatter:off
		return new Resource<>(todo,
			linkTo(methodOn(TodoController.class).one(todo.getId())).withSelfRel(),
			linkTo(methodOn(TodoController.class).all()).withRel("todos"));
		//@formatter:on
	}
}
