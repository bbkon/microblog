package pl.bbkon.microblog.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bbkon.microblog.entry.EntryService;
import pl.bbkon.microblog.user.User;
import pl.bbkon.microblog.user.UserService;

import java.security.Principal;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;

    private UserService userService;
    private EntryService entryService;

    Comment addComment(Integer entryId, CreateCommentRequest request, Principal principal) {
        Comment comment = Comment.builder()
                .contents(request.getContents())
                .author((User) userService.loadUserByUsername(principal.getName()))
                .status(Comment.Status.ORIGINAL)
                .entry(entryService.getOne(entryId))
                .build();
        return commentRepository.save(comment);
    }


    Integer countAllByAuthorUsername(String username) {
        return commentRepository.countAllByAuthorUsername(username);
    }
}
