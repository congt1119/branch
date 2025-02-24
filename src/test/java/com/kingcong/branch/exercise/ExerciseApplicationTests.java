package com.kingcong.branch.exercise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
class ExerciseApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenOctocat_whenGetUser_thenStatus200() throws Exception {
		String expectedResponseJson = Files.readString(ResourceUtils.getFile("classpath:expected/octocat/expected_response.json").toPath());

		mvc.perform(get("/user/octocat")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(expectedResponseJson));
	}

	@Test
	public void givenError404_whenGetUser_thenStatus404() throws Exception {
		mvc.perform(get("/user/error_404")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void givenError500_whenGetUser_thenStatus500() throws Exception {
		mvc.perform(get("/user/error_500")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError());
	}
}
