package pl.bbkon.microblog.entry;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bbkon.microblog.comment.Comment;
import pl.bbkon.microblog.comment.CommentRepository;
import pl.bbkon.microblog.user.User;
import pl.bbkon.microblog.user.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class EntryService {
    private EntryRepository entryRepository;
    private CommentRepository commentRepository;
    private UserService userService;

    public Page<Entry> findAll(Pageable pageable) {
        Page<Entry> entries = entryRepository.findAllByOrderByCreationDateAsc(pageable);
        entries.forEach(entry -> entry.getComments().sort(Comparator.comparing(Comment::getCreationDate)));

        return entries;
    }

    public List<Entry> findAllByAuthor(String username) {
        User user = (User) userService.loadUserByUsername(username);
        return entryRepository.findAllByAuthorOrderByCreationDateAsc(user);
    }

    public Entry add(CreateEntryRequest request) {
        Entry entry = Entry.builder()
                .contents(request.getContents())
                .author((User) userService.loadUserByUsername(request.getUsername()))
                .comments(Collections.emptyList())
                .status(Entry.Status.ORIGINAL)
                .build();
        return entryRepository.save(entry);
    }

    public Entry getOne(Integer id) {
        return entryRepository.getOne(id);
    }
}