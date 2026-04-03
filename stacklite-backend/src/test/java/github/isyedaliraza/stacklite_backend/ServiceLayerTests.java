package github.isyedaliraza.stacklite_backend;

import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import github.isyedaliraza.stacklite_backend.domain.model.WriteQuestionDTO;
import github.isyedaliraza.stacklite_backend.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ServiceLayerTests {
    @Autowired
    private QuestionService questionService;

    Question createQuestion(String title, String body) {
        return questionService.save(WriteQuestionDTO.builder().title(title).body(body).build());
    }

    @Test
    @DisplayName("Throws exception when resolving an unanswered question")
    void shouldNotResolveUnansweredQuestion() {
        Question question1 = createQuestion("Unit Tests", "What are unit tests?");
        assertThrows(IllegalStateException.class, () -> {
            questionService.resolveQuestion(question1.getId());
        });
    }
}
