package pl.bbkon.microblog.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bbkon.microblog.entry.EntryService;
import pl.bbkon.microblog.user.User;
import pl.bbkon.microblog.user.UserService;

@Service
@AllArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;

    private UserService userService;
    private EntryService entryService;

    public Comment addComment(Integer entryId, CreateCommentRequest request) {
        Comment comment = Comment.builder()
                .contents(request.getContents())
                .author((User) userService.loadUserByUsername(request.getUsername()))
                .status(Comment.Status.ORIGINAL)
                .entry(entryService.getOne(entryId))
                .build();
        return commentRepository.save(comment);
    }

    public Integer countAllByAuthorId(Integer authorId) {
        return commentRepository.countAllByAuthorId(authorId);
    }

    public Integer countAllByAuthorUsername(String username) {
        return commentRepository.countAllByAuthorUsername(username);
    }
}
