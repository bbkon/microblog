package pl.bbkon.microblog.tags;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TagService {
    private TagRepository tagRepository;

    public Tag createTagOrReturnExisting(String hashTag) {
        String tag = hashTag.substring(1);
        return tagRepository.findByName(hashTag).orElse(new Tag(tag));
    }

    public void save(Tag tag) {
        tagRepository.save(tag);
    }
}
