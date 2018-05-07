package pl.bbkon.microblog.comment;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/auth/{entryId}/comment")
    public ResponseEntity addComment(@PathVariable("entryId") Integer entryId, @RequestBody CreateCommentRequest comment) {
        commentService.addComment(entryId, comment);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}