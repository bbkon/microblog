package pl.bbkon.microblog.entry;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bbkon.microblog.comment.Comment;
import pl.bbkon.microblog.tags.Tag;
import pl.bbkon.microblog.tags.TagService;
import pl.bbkon.microblog.user.User;
import pl.bbkon.microblog.user.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;

@Service
@AllArgsConstructor
public class EntryService {
    private EntryRepository entryRepository;
    private UserService userService;
    private TagService tagService;


    public Page<Entry> findAll(Pageable pageable) {
        Page<Entry> entries = entryRepository.findAllByOrderByCreationDateDesc(pageable);
        entries.forEach(entry -> entry.getComments().sort(Comparator.comparing(Comment::getCreationDate)));

        return entries;
    }

    public List<Entry> findAllByAuthor(String username) {
        User user = (User) userService.loadUserByUsername(username);
        return entryRepository.findAllByAuthorOrderByCreationDateDesc(user);
    }

    public Page<Entry> findAllByTag(Tag tag, Pageable pageable) {
        return entryRepository.findByTags(tag, pageable);
    }

    public Entry add(CreateEntryRequest request, Principal principal) {
        Entry entry = Entry.builder()
                .contents(request.getContents())
                .author((User) userService.loadUserByUsername(principal.getName()))
                .comments(Collections.emptyList())
                .status(Entry.Status.ORIGINAL)
                .build();
        assignTagsToEntry(entry);
        return entryRepository.save(entry);
    }

    private void assignTagsToEntry(Entry entry) {
        Matcher m = Tag.PATTERN.matcher(entry.getContents());
        while (m.find()) {
            Tag currentTag = tagService.createTagOrReturnExisting(m.group());
            entry.addTag(currentTag);

        }
    }

    public Entry getOne(Integer id) {
        return entryRepository.getOne(id);
    }

    public Integer countAllByAuthorUsername(String username) {
        return entryRepository.countAllByAuthorUsername(username);
    }

    public Integer upvoteEntry(Integer entryId, Principal principal) {
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new NoSuchElementException("Entry not found."));
        User user = (User) userService.loadUserByUsername(principal.getName());
        entry.upvote(user);
        entryRepository.save(entry);
        return entry.getVotes();
    }


}