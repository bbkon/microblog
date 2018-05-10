package pl.bbkon.microblog.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import pl.bbkon.microblog.comment.Comment;
import pl.bbkon.microblog.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "entries")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entry implements Votable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Integer id;

    @Column(nullable = false)
    @Size(min = 3, max = 5000)
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

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Builder.Default
    private Integer votes = 0;

    public enum Status {
        ORIGINAL,
        EDITED
    }

    public String getAuthorName() {
        return author.getUsername();
    }

    @Override
    public void upvote() {
        votes++;
    }

    @Override
    public void downvote() {
        votes--;
    }
}