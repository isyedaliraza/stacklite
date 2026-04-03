package github.isyedaliraza.stacklite_backend;

import github.isyedaliraza.stacklite_backend.domain.entity.Answer;
import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import github.isyedaliraza.stacklite_backend.domain.repository.AnswerRepository;
import github.isyedaliraza.stacklite_backend.domain.repository.QuestionRepository;
import github.isyedaliraza.stacklite_backend.domain.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryLayerTests {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AnswerRepository answerRepository;

    private Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    private Question createQuestion(String title, String body) {
        Question question = new Question();
        question.setTitle(title);
        question.setBody(body);
        return questionRepository.save(question);
    }

    private Question createQuestion(String title, String body, List<Tag> tags) {
        Question question = new Question();
        question.setTitle(title);
        question.setBody(body);
        question.setTags(tags);
        return questionRepository.save(question);
    }

    private Answer createAnswer(String body, Question question) {
        Answer answer = new Answer();
        answer.setBody(body);
        answer.setQuestion(question);
        return answerRepository.save(answer);
    }

    @Test
    @DisplayName("Should save and retrieve the same question")
    void shouldRetrieveQuestionWhenSaved() {
        Question question = createQuestion("Unit Tests", "What are unit tests?");

        assertThat(question.getId()).isNotNull();

        Optional<Question> result = questionRepository.findById(question.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Unit Tests");
        assertThat(result.get().getBody()).isEqualTo("What are unit tests?");
    }

    @Test
    @DisplayName("Should return all the questions whose title matches a keyword")
    void shouldReturnQuestionsWhenTitleContainsKeyword() {
        createQuestion("Unit Tests", "What are unit tests?");
        createQuestion("Java", "What are static imports in Java?");

        List<Question> result = questionRepository.findAllByTitleContainingIgnoreCase("Unit Tests");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Unit Tests");

        result = questionRepository.findAllByTitleContainingIgnoreCase("unit tests");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Unit Tests");
    }

    @Test
    @DisplayName("Should return questions when tag name matches keyword")
    void shouldReturnQuestionsWhenTagNameMatchesKeyword() {
        Tag tag1 = createTag("Testing");
        Tag tag2 = createTag("Programming");
        createQuestion("Unit Tests", "What are unit tests?", List.of(tag1));
        createQuestion("Java", "What are static imports in Java?", List.of(tag2));

        List<Question> result = questionRepository.findAllByTags_Name("Testing");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Unit Tests");
        assertThat(result.get(0).getTags()).contains(tag1);
        assertThat(result.get(0).getTags()).doesNotContain(tag2);
    }

    @Test
    @DisplayName("Should return unanswered questions")
    void shouldReturnUnansweredQuestions() {
        createQuestion("Java", "What are static imports in Java?");
        Question question1 = createQuestion("Unit Tests", "What are unit tests?");
        createAnswer("Unit tests are code fragments that validates the behavior of software units.",
                question1);

        List<Question> result = questionRepository.findAllByAnswersIsEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Java");
    }

    @Test
    @DisplayName("Should return answers by question ID")
    void shouldReturnAnswersByQuestionId() {
        createQuestion("Unit Tests", "What are unit tests?");
        Question question2 = createQuestion("Java", "What are static imports in Java?");
        createAnswer("Static imports are new Java feature", question2);
        createAnswer("Static imports are cool", question2);

        List<Answer> result = answerRepository.findAllByQuestion_Id(question2.getId());
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getQuestion().getId()).isEqualTo(question2.getId());
        assertThat(result.get(1).getQuestion().getId()).isEqualTo(question2.getId());
    }

    @Test
    @DisplayName("Should return no questions when a new tag name is used")
    void shouldReturnEmptyResultForNewTag() {
        createQuestion("Java", "What are static imports in Java?");
        createQuestion("Unit Tests", "What are unit tests?");

        List<Question> result = questionRepository.findAllByTags_Name("Fruits");
        assertThat(result).isEmpty();
    }
}
