package github.isyedaliraza.stacklite_backend.controller;

import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import github.isyedaliraza.stacklite_backend.domain.model.QuestionDTO;
import github.isyedaliraza.stacklite_backend.domain.model.WriteQuestionDTO;
import github.isyedaliraza.stacklite_backend.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/questions")
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuestionDTO> getAll() {
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question: questionService.findAll()) {
            questions.add(new QuestionDTO(question));
        }
        return questions;
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody WriteQuestionDTO writeQuestionDTO) {
        try {
            Question question = questionService.save(writeQuestionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new QuestionDTO(question));
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable String id, @RequestBody WriteQuestionDTO writeQuestionDTO) {
        try {
            Question question = questionService.update(id, writeQuestionDTO);
            return ResponseEntity.ok(new QuestionDTO(question));
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getById(@PathVariable String id) {
        try {
            Question question = questionService.findById(id).orElseThrow();
            return ResponseEntity.ok(new QuestionDTO(question));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        questionService.deleteById(id);
    }

    @GetMapping("/tags/{name}")
    public List<QuestionDTO> getQuestionsByTag(@PathVariable String name) {
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question: questionService.findAllByTagsName(name)) {
            questions.add(new QuestionDTO(question));
        }

        return questions;
    }

    @GetMapping("/search")
    public List<QuestionDTO> search(@RequestParam(required = false) String title) {
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question: questionService.findAllByTitleContainingIgnoreCase(title)) {
            questions.add(new QuestionDTO(question));
        }

        return questions;
    }

    @GetMapping("/unanswered")
    public List<QuestionDTO> getUnansweredQuestions() {
        List<QuestionDTO> questions = new ArrayList<>();
        for (Question question: questionService.findAllByAnswersIsEmpty()) {
            questions.add(new QuestionDTO(question));
        }

        return questions;
    }

    @PutMapping("/{questionId}/tags/{tagId}")
    public ResponseEntity<?> assignTagToQuestion(@PathVariable String questionId, @PathVariable String tagId) {
        try {
            Question question = questionService.assignTagToQuestion(questionId, tagId);
            return ResponseEntity.ok(new QuestionDTO(question));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{questionId}/resolve")
    public ResponseEntity<?> resolveQuestion(@PathVariable String questionId) {
        try {
            Question question = questionService.resolveQuestion(questionId);
            return ResponseEntity.ok(new QuestionDTO(question));
        } catch (IllegalStateException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
