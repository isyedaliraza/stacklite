package github.isyedaliraza.stacklite_backend.controller;

import github.isyedaliraza.stacklite_backend.domain.entity.Answer;
import github.isyedaliraza.stacklite_backend.domain.model.AnswerDTO;
import github.isyedaliraza.stacklite_backend.domain.model.WriteAnswerDTO;
import github.isyedaliraza.stacklite_backend.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/answers")
@CrossOrigin
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public List<AnswerDTO> getAll() {
        List<AnswerDTO> answers = new ArrayList<>();
        for (Answer answer: answerService.findAll()) {
            answers.add(new AnswerDTO(answer));
        }
        return answers;
    }

    @PostMapping
    public ResponseEntity<?> createAnswer(@RequestBody WriteAnswerDTO writeAnswerDTO) {
        try {
            Answer answer = answerService.save(writeAnswerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new AnswerDTO(answer));
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnswer(@PathVariable String id, @RequestBody WriteAnswerDTO writeAnswerDTO) {
        try {
            Answer answer = answerService.update(id, writeAnswerDTO);
            return ResponseEntity.ok(new AnswerDTO(answer));
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDTO> getById(@PathVariable String id) {
        try {
            Answer answer = answerService.findById(id).orElseThrow();
            return ResponseEntity.ok(new AnswerDTO(answer));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        answerService.deleteById(id);
    }

    @GetMapping("/question/{id}")
    public List<AnswerDTO> getAnswersByQuestionId(@PathVariable String id) {
        List<AnswerDTO> answers = new ArrayList<>();
        for (Answer answer: answerService.findAllByQuestion_Id(id)) {
            answers.add(new AnswerDTO(answer));
        }
        return answers;
    }
}
