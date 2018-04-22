package pl.bbkon.microblog.entry;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import pl.bbkon.microblog.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Size(min = 3, max = 200)
    private String contents;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private Date creationDate;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, updatable = false)
    private Kind kind;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    enum Status {
        ORIGINAL,
        EDITED
    }

    enum Kind {
        ENTRY,
        COMMENT
    }
}
