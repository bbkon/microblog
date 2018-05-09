package pl.bbkon.microblog.entry;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EntryController {
    private EntryService entryService;

    @GetMapping("/unauth/entries")
    public ResponseEntity<Page<Entry>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(entryService.findAll(pageable));
    }

    @GetMapping("/unauth/{username}/entries")
    public ResponseEntity<List<Entry>> findAllByUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(entryService.findAllByAuthor(username));
    }

    @PostMapping("/auth/entry")
    public ResponseEntity<Entry> addEntry(@RequestBody CreateEntryRequest request) {
        return ResponseEntity.ok(entryService.add(request));
    }

    @GetMapping("/auth/{username}/entriesNumber")
    public ResponseEntity<Integer> getCommentsNumber(@PathVariable("username") String username) {
        return new ResponseEntity<>(entryService.countAllByAuthorUsername(username), HttpStatus.OK);
    }
}