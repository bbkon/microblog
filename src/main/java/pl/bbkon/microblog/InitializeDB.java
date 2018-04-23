package pl.bbkon.microblog;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bbkon.microblog.role.Role;
import pl.bbkon.microblog.role.RoleEnum;
import pl.bbkon.microblog.user.User;
import pl.bbkon.microblog.user.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
@AllArgsConstructor
public class InitializeDB {

    private UserRepository repository;
    private PasswordEncoder encoder;

    @PostConstruct
    public void createUsers() {
        repository.save(User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .email("admin@admin.o2.pl")
                .status(User.Status.ACTIVE)
                .logo("logo")
                .authorities(Collections.singletonList(new Role(RoleEnum.USER)))
                .description("no description")
                .build());
    }

//    @PostConstruct
//    public void createEntriesWithComments() {
//        List<Comment> comments = new ArrayList<>();
//        comments.add()
//    }

}