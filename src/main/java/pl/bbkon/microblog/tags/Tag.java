package pl.bbkon.microblog.tags;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.regex.Pattern;

@Entity
@Data
@Table(name = "tags")
@NoArgsConstructor
public class Tag {
    private static final String REGEX = "(?:(?<=\\s)|^)#(\\w*[A-Za-z_]+\\w*)";
    public static final Pattern PATTERN = Pattern.compile(REGEX);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    public Tag(@NotEmpty String name) {
        this.name = name;
    }
}
