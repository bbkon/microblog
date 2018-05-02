package pl.bbkon.microblog.entry;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bbkon.microblog.user.User;
import pl.bbkon.microblog.user.UserService;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class EntryService {
    private EntryRepository entryRepository;
    private UserService userService;

    public List<Entry> findAll() {
        return entryRepository.findAllByOrderByCreationDateDesc();
    }

    public Page<Entry> findAll(Pageable pageable) {
        return entryRepository.findAllByOrderByCreationDateDesc(pageable);
    }

    public List<Entry> findAllByAuthor(String username) {
        User user = (User) userService.loadUserByUsername(username);
        return entryRepository.findAllByAuthorOrderByCreationDateDesc(user);
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
}
