package com.patrikturi.todoapp.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patrikturi.todoapp.data.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
