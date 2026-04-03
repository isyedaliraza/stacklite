package github.isyedaliraza.stacklite_backend;

import github.isyedaliraza.stacklite_backend.domain.model.WriteQuestionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerLayerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test create question")
    void shouldCreateQuestion() throws Exception {
        WriteQuestionDTO data = WriteQuestionDTO.builder().title("Unit Tests").body("What are unit tests?").build();
        mockMvc.perform(post("/api/v1/questions").contentType(MediaType.APPLICATION_JSON).content(
                objectMapper.writeValueAsString(data))).andDo(print()).andExpect(status().isCreated()).andExpect(
                jsonPath("$.title").value("Unit Tests")).andExpect(jsonPath("$.body")
                .value("What are unit tests?"));
    }
}
