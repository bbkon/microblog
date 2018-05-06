package pl.bbkon.microblog.comment;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class CreateCommentRequest {

    private String username;
    private String contents;

}
