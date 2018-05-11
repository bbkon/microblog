package pl.bbkon.microblog.comment;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@Data
@Validated
class CreateCommentRequest {

    @Size(min = 3, max = 5000)
    private String contents;

}
