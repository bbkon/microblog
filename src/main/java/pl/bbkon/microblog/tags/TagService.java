package pl.bbkon.microblog.tags;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TagService {
    private TagRepository tagRepository;

    public Tag createNewOrGetExisting(String hashTag) {
        String tagName = hashTag.substring(1);
        Optional<Tag> optionalTag = tagRepository.findFirstByName(tagName);
        return optionalTag.orElseGet(() -> tagRepository.save(new Tag(tagName)));
    }

    public Tag findByName(String tagName) {
        return tagRepository.findFirstByName(tagName).orElseThrow(() -> new NoSuchElementException("No such tag found"));
    }
}
