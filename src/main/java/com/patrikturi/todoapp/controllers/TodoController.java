package com.patrikturi.todoapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	TodoRepository repository;

	@GetMapping("/todos")
	public List<Todo> all() {
		return repository.findAll();
	}

	@PostMapping("/todos")
	public Todo newTodo(@RequestBody Todo todo) {
		return repository.save(todo);
	}

	@GetMapping("/todos/{id}")
	public Todo one(@PathVariable("id") Long id) {

		return repository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
	}

	@PutMapping("/todos/{id}")
	Todo replaceTodo(@RequestBody Todo newTodo, @PathVariable Long id) {

		return repository.findById(id).map(todo -> {
			todo.setTitle(newTodo.getTitle());
			todo.setOrder(newTodo.getOrder());
			todo.setCompleted(newTodo.getCompleted());
			return repository.save(todo);
		}).orElseGet(() -> {
			newTodo.setId(id);
			return repository.save(newTodo);
		});
	}

	@DeleteMapping("/todos/{id}")
	void deleteTodo(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
