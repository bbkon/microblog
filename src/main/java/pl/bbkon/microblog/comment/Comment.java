package pl.bbkon.microblog.comment;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.bbkon.microblog.entry.Entry;
import pl.bbkon.microblog.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "comments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer id;

    @Column(nullable = false)
    @Size(min = 3, max = 200)
    private String contents;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;

    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User author;

    @ManyToOne
    @JoinColumn(name = "entry_id", nullable = false)
    @JsonIgnore
    private Entry entry;

    public String getAuthorName() {
        return author.getUsername();
    }

    public enum Status {
        ORIGINAL,
        EDITED
    }
}
