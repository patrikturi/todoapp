package com.patrikturi.todoapp.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Todo {

	@Id
	@GeneratedValue
	private Long id;

	private String title;

	// Use custom name because it would collide with an sql keyword
	@Column(name = "TODO_ORDER")
	private Integer order;

	private Boolean completed;

	private Todo() {
	}

	public Todo(Long id, String title, Integer order, Boolean completed) {
		this.id = id;
		this.title = title;
		this.order = order;
		this.completed = completed;
	}
}
