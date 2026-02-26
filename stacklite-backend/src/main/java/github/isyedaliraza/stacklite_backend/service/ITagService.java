package github.isyedaliraza.stacklite_backend.service;

import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import github.isyedaliraza.stacklite_backend.domain.model.WriteTagDTO;
import github.isyedaliraza.stacklite_backend.domain.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ITagService implements TagService{
    private final TagRepository tagRepository;

    public ITagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    private void requireNotBlank(String s, String field) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    @Override
    @Transactional
    public Iterable<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public Tag save(WriteTagDTO writeTagDTO) {
        if (writeTagDTO == null) {
            throw new IllegalArgumentException("writeTagDTO is required");
        }

        Tag tag = new Tag();
        tag.setName(writeTagDTO.getName());

        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public Optional<Tag> findById(String id) {
        return tagRepository.findById(id);
    }

    @Override
    @Transactional
    public Tag update(String id, WriteTagDTO writeTagDTO) {
        Tag tag = tagRepository.findById(id).orElseThrow();

        requireNotBlank(writeTagDTO.getName(), "Name");

        tag.setName(writeTagDTO.getName());

        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> findAllByNameContainingIgnoreCase(String keyword) {
        return tagRepository.findAllByNameContainingIgnoreCase(keyword);
    }
}
