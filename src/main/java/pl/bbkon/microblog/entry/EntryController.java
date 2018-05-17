package pl.bbkon.microblog.entry;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bbkon.microblog.tags.TagService;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class EntryController {
    private EntryService entryService;
    private TagService tagService;

    @GetMapping("/unauth/entries")
    public ResponseEntity<Page<Entry>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(entryService.findAll(pageable));
    }

    @GetMapping("/unauth/entries/user/{username}")
    public ResponseEntity<List<Entry>> findAllByUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(entryService.findAllByAuthor(username));
    }

    @GetMapping("/unauth/entries/tag/{tagName}")
    public ResponseEntity<Page<Entry>> findAllByTag(@PathVariable("tagName") String tagName,
                                                    @PageableDefault(size = 20) Pageable pageable) {
        return new ResponseEntity<>(entryService.findAllByTag(tagService.findByName(tagName), pageable), HttpStatus.OK);
    }

    @PostMapping("/auth/entry")
    public ResponseEntity<Entry> addEntry(@RequestBody CreateEntryRequest request, Principal principal) {
        return ResponseEntity.ok(entryService.add(request, principal));
    }

    @GetMapping("/auth/{username}/entriesNumber")
    public ResponseEntity<Integer> getCommentsNumber(@PathVariable("username") String username) {
        return new ResponseEntity<>(entryService.countAllByAuthorUsername(username), HttpStatus.OK);
    }

    @GetMapping("/auth/entry/{entryId}/upvote")
    public ResponseEntity<Integer> upvoteEntry(@PathVariable("entryId") Integer entryId, Principal principal) {
        return new ResponseEntity<>(entryService.upvoteEntry(entryId, principal), HttpStatus.OK);
    }
}