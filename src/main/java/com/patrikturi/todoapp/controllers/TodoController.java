package com.patrikturi.todoapp.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.patrikturi.todoapp.data.entity.Todo;
import com.patrikturi.todoapp.data.repository.TodoRepository;
import com.patrikturi.todoapp.errors.TodoNotFoundException;

@RestController
public class TodoController {

	@Autowired
	private TodoRepository repository;
	@Autowired
	private TodoResourceAssembler assembler;

	@GetMapping("/todos")
	public Resources<Resource<Todo>> all() {
		//@formatter:off
		List<Resource<Todo>> todos = repository.findAll().stream().
				map(assembler::toResource)
				.collect(Collectors.toList());
		//@formatter:on

		return new Resources<>(todos, linkTo(methodOn(TodoController.class).all()).withSelfRel());
	}

	@PostMapping("/todos")
	public ResponseEntity<?> newTodo(@RequestBody Todo todo) throws URISyntaxException {

		Resource<Todo> resource = assembler.toResource(repository.save(todo));

		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

	@GetMapping("/todos/{id}")
	public Resource<Todo> one(@PathVariable("id") Long id) {

		Todo todo = repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
		return assembler.toResource(todo);
	}

	@PutMapping("/todos/{id}")
	ResponseEntity<?> replaceTodo(@RequestBody Todo newTodo, @PathVariable Long id) throws URISyntaxException {

		Todo updatedTodo = repository.findById(id).map(todo -> {
			todo.setTitle(newTodo.getTitle());
			todo.setOrder(newTodo.getOrder());
			todo.setCompleted(newTodo.getCompleted());
			return repository.save(todo);
		}).orElseGet(() -> {
			newTodo.setId(id);
			return repository.save(newTodo);
		});

		Resource<Todo> resource = assembler.toResource(updatedTodo);

		return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
	}

	@DeleteMapping("/todos/{id}")
	ResponseEntity<?> deleteTodo(@PathVariable Long id) {
		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
