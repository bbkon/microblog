package pl.bbkon.microblog.comment;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/auth/{entryId}/comment")
    public ResponseEntity<Comment> addComment(@PathVariable("entryId") Integer entryId, @RequestBody CreateCommentRequest comment) {
        return new ResponseEntity<>(commentService.addComment(entryId, comment), HttpStatus.OK);
    }

    @GetMapping("/auth/{username}/commentsNumber")
    public ResponseEntity<Integer> getCommentsNumber(@PathVariable String username) {
        return new ResponseEntity<>(commentService.countAllByAuthorUsername(username), HttpStatus.OK);
    }
}
