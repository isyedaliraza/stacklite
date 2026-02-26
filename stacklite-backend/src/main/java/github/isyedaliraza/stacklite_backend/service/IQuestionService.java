package github.isyedaliraza.stacklite_backend.service;

import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import github.isyedaliraza.stacklite_backend.domain.model.WriteQuestionDTO;
import github.isyedaliraza.stacklite_backend.domain.repository.QuestionRepository;
import github.isyedaliraza.stacklite_backend.domain.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IQuestionService implements QuestionService {
    private final QuestionRepository questionRepository;
    private final TagRepository tagRepository;

    public IQuestionService(QuestionRepository questionRepository, TagRepository tagRepository) {
        this.questionRepository = questionRepository;
        this.tagRepository = tagRepository;
    }

    private void requireNotBlank(String s, String field) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    private void validateWriteQuestionDTO(WriteQuestionDTO writeQuestionDTO) {
        requireNotBlank(writeQuestionDTO.getTitle(), "Title");
        requireNotBlank(writeQuestionDTO.getBody(), "Body");
    }

    @Override
    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question save(WriteQuestionDTO writeQuestionDTO) {
        if (writeQuestionDTO == null) {
            throw new IllegalArgumentException("writeQuestionDTO is required");
        }

        validateWriteQuestionDTO(writeQuestionDTO);

        Question question = new Question();
        question.setTitle(writeQuestionDTO.getTitle());
        question.setBody(writeQuestionDTO.getBody());

        String tagId = writeQuestionDTO.getTagId();
        if (tagId != null) {
            Tag tag = tagRepository.findById(tagId).orElseThrow();
            question.setTags(List.of(tag));
        }

        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> findById(String id) {
        return questionRepository.findById(id);
    }

    @Override
    public Question update(String id, WriteQuestionDTO writeQuestionDTO) {
        Question question = questionRepository.findById(id).orElseThrow();

        String title = writeQuestionDTO.getTitle();
        String body = writeQuestionDTO.getBody();

        if (title != null) {
            question.setTitle(title);
        }

        if (body != null) {
            question.setBody(body);
        }

        return questionRepository.save(question);
    }

    @Override
    public void deleteById(String id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<Question> findAllByTagsName(String name) {
        return questionRepository.findAllByTagsName(name);
    }

    @Override
    public List<Question> findAllByTitleContainingIgnoreCase(String keyword) {
        return questionRepository.findAllByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Question> findAllByAnswersIsEmpty() {
        return questionRepository.findAllByAnswersIsEmpty();
    }

    @Override
    public Question assignTagToQuestion(String questionId, String tagId) {
        Question question = questionRepository.findById(questionId).orElseThrow();
        Tag tag = tagRepository.findById(tagId).orElseThrow();

        List<Tag> tags = question.getTags();
        if (!tags.contains(tag)) {
            tags.add(tag);
            question.setTags(tags);
        }

        return questionRepository.save(question);
    }

    @Override
    public Question resolveQuestion(String questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow();

        if (question.getAnswers().isEmpty()) {
            throw new IllegalStateException("Question must have an answer to resolve.");
        }

        question.setAnswered(true);
        return questionRepository.save(question);
    }
}
