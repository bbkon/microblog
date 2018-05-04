package pl.bbkon.microblog.entry;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EntryController {
    private EntryService entryService;

    @GetMapping("/unauth/entries")
    public ResponseEntity<Page<Entry>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        Page<Entry> serviceAll = entryService.findAll(pageable);
//        serviceAll.forEach(res -> {
//            List<Comment> comments = res.getComments();
//            if (!comments.equals(null)) {
//                comments.forEach(res2 -> res2.setEntry(null));
//                res.setComments(comments);
//            }
//        });

        return ResponseEntity.ok(serviceAll);
    }

    @GetMapping("/unauth/{username}/entries")
    public ResponseEntity<List<Entry>> findAllByUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(entryService.findAllByAuthor(username));
    }

    @PostMapping("/auth/entry")
    public ResponseEntity<Entry> addEntry(@RequestBody CreateEntryRequest request) {
        return ResponseEntity.ok(entryService.add(request));
    }
}
