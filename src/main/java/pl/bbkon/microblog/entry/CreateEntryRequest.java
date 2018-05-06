package pl.bbkon.microblog.entry;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
class CreateEntryRequest {

    private String username;
    private String contents;


}
