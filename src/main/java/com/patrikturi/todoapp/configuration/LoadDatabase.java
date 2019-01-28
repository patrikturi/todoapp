package com.patrikturi.todoapp.configuration;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.patrikturi.todoapp.data.entity.Todo;
import com.patrikturi.todoapp.data.repository.TodoRepository;

@Configuration
@Slf4j
public class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(TodoRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Todo(new Long(1), "Task C", new Integer(3), Boolean.FALSE)));
			log.info("Preloading " + repository.save(new Todo(new Long(2), "Task B", new Integer(2), Boolean.TRUE)));
			log.info("Preloading " + repository.save(new Todo(new Long(3), "Task C", new Integer(1), Boolean.FALSE)));
		};
	}
}
