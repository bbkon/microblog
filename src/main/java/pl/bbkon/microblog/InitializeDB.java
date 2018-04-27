package pl.bbkon.microblog;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bbkon.microblog.entry.Entry;
import pl.bbkon.microblog.entry.EntryRepository;
import pl.bbkon.microblog.role.Role;
import pl.bbkon.microblog.role.RoleEnum;
import pl.bbkon.microblog.user.User;
import pl.bbkon.microblog.user.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class InitializeDB {

    private UserRepository userRepo;
    private EntryRepository entryRepository;
    private PasswordEncoder encoder;

    @PostConstruct
    public void fillUpDB() {
        createUsers();
        createEntries();
    }

    private void createUsers() {
        userRepo.save(createAdmin());
        userRepo.save(createUser());
    }

    private User createAdmin() {
        return User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .email("admin@admin.o2.pl")
                .status(User.Status.ACTIVE)
                .logo("logo")
                .authorities(Collections.singletonList(new Role(RoleEnum.ADMIN)))
                .description("no description")
                .build();
    }

    private User createUser() {
        return User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .email("user@o2.pl")
                .status(User.Status.ACTIVE)
                .logo("logo")
                .authorities(Collections.singletonList(new Role(RoleEnum.USER)))
                .description("some description")
                .build();
    }

    private void createEntries() {
        List<String> contentsList = createListOfContents();

        for (int i = 0; i < 40; i++) {
            int num = getRandomNumber(contentsList.size());
            entryRepository.save(Entry.builder()
                    .author(userRepo.getOne(i % 2 + 1))
                    .contents(contentsList.get(num))
                    .status(Entry.Status.ORIGINAL)
                    .comments(Collections.emptyList()).build());
        }
    }

    private int getRandomNumber(int bound) {
        Random r = new Random();
        return r.nextInt(bound);
    }

    private List<String> createListOfContents() {
        List<String> contentsList = new ArrayList<>();
        contentsList.add("film do obejrzenia z dziewczyną, najlepiej romantyczny");
        contentsList.add("Wyczuwam dobry materiał na gównoburzę");
        contentsList.add("Sprzedam 14 miesięcznych abonamentów na #spotify.");
        contentsList.add("Chciałbym na ścianie w biurze umieścić ekran do wyświetlania reklam");
        contentsList.add("Nowa inwestycja mieszkaniowa w Warszawie. Apartamenty Dolny Mokotów powstaną przy ul.");
        contentsList.add("Jaki sklep z akcesoriami motocyklowymi polecacie w #warszawa?");
        contentsList.add("Lyknijcie redpilla od @maniakdvd normidla ");
        return contentsList;
    }
}