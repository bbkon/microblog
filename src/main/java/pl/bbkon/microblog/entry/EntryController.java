package pl.bbkon.microblog.entry;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class EntryController {
    private EntryService entryService;

    @GetMapping("/unauth/entries")
    public List<Entry> findAll() {
        return entryService.findAll();
    }

    @GetMapping("/unauth/{username}/entries")
    public List<Entry> findAllByUser(@PathVariable("username") String username) {
        return entryService.findAllByAuthor(username);
    }

}
