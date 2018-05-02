package pl.bbkon.microblog.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.userdetails.UserDetails;
import pl.bbkon.microblog.role.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;

    @Column(nullable = false)
    @Size(min = 4, max = 100, message = "Password must be at least 4 characters long")
    private String password;

    @Column(nullable = false)
    @Email(message = "Incorrect email format")
    private String email;

    private String description;
    private String logo;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date creationDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Cascade(CascadeType.ALL)
    private List<Role> authorities;

    @Column(nullable = false)
    private Status status;

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !status.equals(Status.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return !status.equals(Status.INACTIVE);
    }

    public enum Status {
        ACTIVE,
        INACTIVE,
        BANNED
    }
}