package com.patrikturi.todoapp;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetOne() throws Exception {

		mockMvc.perform(get("/todos/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Task C")))
				.andExpect(jsonPath("$.order", is(new Integer(3))))
				.andExpect(jsonPath("$.completed", is(Boolean.FALSE)));

		mockMvc.perform(get("/todos/3")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Task A")))
				.andExpect(jsonPath("$.order", is(new Integer(1))))
				.andExpect(jsonPath("$.completed", is(Boolean.TRUE)));
	}

	@Test
	public void testGetAll() throws Exception {
		mockMvc.perform(get("/todos")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.todoList[0].title", is("Task C")))
				.andExpect(jsonPath("$._embedded.todoList[1].title", is("Task B")))
				.andExpect(jsonPath("$._embedded.todoList[2].title", is("Task A")));

	}

	@Test
	public void testPost() throws Exception {

		MockHttpServletRequestBuilder postRequest = MockMvcRequestBuilders.post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"title\": \"New Todo\", \"order\": 10, \"completed\": \"true\"}");

		mockMvc.perform(postRequest).andExpect(status().isCreated())
				.andExpect(jsonPath("$.title", is("New Todo")))
				.andExpect(jsonPath("$.order", is(new Integer(10))))
				.andExpect(jsonPath("$.completed", is(Boolean.TRUE)));
	}
}
