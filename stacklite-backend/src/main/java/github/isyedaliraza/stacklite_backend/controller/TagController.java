package github.isyedaliraza.stacklite_backend.controller;

import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import github.isyedaliraza.stacklite_backend.domain.model.TagDTO;
import github.isyedaliraza.stacklite_backend.domain.model.WriteTagDTO;
import github.isyedaliraza.stacklite_backend.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/tags")
@CrossOrigin
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDTO> getAll() {
        List<TagDTO> tags = new ArrayList<>();
        for (Tag tag: tagService.findAll()) {
            tags.add(new TagDTO(tag));
        }
        return tags;
    }

    @PostMapping
    public ResponseEntity<TagDTO> createTag(@RequestBody WriteTagDTO writeTagDTO) {
        try {
            Tag tag = tagService.save(writeTagDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new TagDTO(tag));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable String id, @RequestBody WriteTagDTO writeTagDTO) {
        try {
            Tag tag = tagService.update(id, writeTagDTO);
            return ResponseEntity.ok(new TagDTO(tag));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getById(@PathVariable String id) {
        try {
            Tag tag = tagService.findById(id).orElseThrow();
            return ResponseEntity.ok(new TagDTO(tag));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        tagService.deleteById(id);
    }

    @GetMapping("/search")
    public List<TagDTO> search(@RequestParam(required = false) String keyword) {
        List<TagDTO> tags = new ArrayList<>();
        for (Tag tag: tagService.findAllByNameContainingIgnoreCase(keyword)) {
            tags.add(new TagDTO(tag));
        }
        return tags;
    }
}
