package pl.bbkon.microblog.tags;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TagService {
    private TagRepository tagRepository;

    //    public Tag createNewOrGetExisting(String hashTag) {
//        String tagName = hashTag.substring(1);
//        return tagRepository.findFirstByName(tagName).orElse(tagRepository.save(new Tag(tagName)));
//    }
    public Tag createNewOrGetExisting(String hashTag) {
        String tagName = hashTag.substring(1);
        Optional<Tag> optionalTag = tagRepository.findByName(tagName);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        }
        return tagRepository.save(new Tag(tagName));
    }

    public Tag findByName(String tagName) {
        return tagRepository.findFirstByName(tagName).get();
    }
}
