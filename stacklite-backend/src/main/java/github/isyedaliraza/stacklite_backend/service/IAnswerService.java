package github.isyedaliraza.stacklite_backend.service;

import github.isyedaliraza.stacklite_backend.domain.entity.Answer;
import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import github.isyedaliraza.stacklite_backend.domain.model.WriteAnswerDTO;
import github.isyedaliraza.stacklite_backend.domain.repository.AnswerRepository;
import github.isyedaliraza.stacklite_backend.domain.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IAnswerService implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public IAnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    private void requireNotBlank(String s, String field) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    private void validateWriteAnswerDTO(WriteAnswerDTO writeAnswerDTO) {
        requireNotBlank(writeAnswerDTO.getBody(), "Title");
        requireNotBlank(writeAnswerDTO.getQuestionId(), "QuestionID");
    }

    @Override
    public Iterable<Answer> findAll() {
        return answerRepository.findAll();
    }

    @Override
    public Answer save(WriteAnswerDTO writeAnswerDTO) {
        if (writeAnswerDTO == null) {
            throw new IllegalArgumentException("writeAnswerDTO is required");
        }

        validateWriteAnswerDTO(writeAnswerDTO);

        Answer answer = new Answer();
        answer.setBody(writeAnswerDTO.getBody());

        Question question = questionRepository.findById(writeAnswerDTO.getQuestionId()).orElseThrow();
        answer.setQuestion(question);

        return answerRepository.save(answer);
    }

    @Override
    public Optional<Answer> findById(String id) {
        return answerRepository.findById(id);
    }

    @Override
    public Answer update(String id, WriteAnswerDTO writeAnswerDTO) {
        Answer answer = answerRepository.findById(id).orElseThrow();

        String body = writeAnswerDTO.getBody();

        if (body != null) {
            answer.setBody(body);
        }

        return answerRepository.save(answer);
    }

    @Override
    public void deleteById(String id) {
        answerRepository.deleteById(id);
    }

    @Override
    public List<Answer> findAllByQuestion_Id(String questionId) {
        return answerRepository.findAllByQuestion_Id(questionId);
    }
}
