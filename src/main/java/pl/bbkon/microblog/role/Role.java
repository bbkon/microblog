package pl.bbkon.microblog.role;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Table(name = "roles")
@Entity
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String authority;

    public Role(RoleEnum authority) {
        this.authority = authority.toString();
    }

    @Override
    public String toString() {
        return authority;
    }
}
