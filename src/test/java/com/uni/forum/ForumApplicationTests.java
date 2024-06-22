package com.uni.forum;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uni.forum.domain.dtos.UserDto;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: extends the tests
@SpringBootTest
@AutoConfigureMockMvc
class ForumApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@ClassRule
	public static PostgreSQLContainer<ForumContainer> postgreSQLContainer = ForumContainer.getInstance();

	@Test
	void contextLoads() throws Exception {
		mockMvc.perform(post("/users")
				.content(asJsonString(new UserDto("test-username", "test-user", "test-user", "test-user", "asd")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		mockMvc.perform(get("/users/test-username"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("test-username")));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void newTest(final Object obj) {
		Assert.assertTrue(true);
	}

}
