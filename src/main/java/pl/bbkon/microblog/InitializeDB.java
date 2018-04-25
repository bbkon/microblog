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
    public void createUsers() {
        userRepo.save(User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .email("admin@admin.o2.pl")
                .status(User.Status.ACTIVE)
                .logo("logo")
                .authorities(Collections.singletonList(new Role(RoleEnum.USER)))
                .description("no description")
                .build());
    }

    @PostConstruct
    public void createEntries() {
        List<String> contentsList = createListOfContents();

        for (int i = 0; i < 20; i++) {
            int num = generateRandomNumber(contentsList.size());
            entryRepository.save(Entry.builder()
                    .author(userRepo.getOne(1))
                    .contents(contentsList.get(num))
                    .status(Entry.Status.ORIGINAL)
                    .comments(Collections.emptyList()).build());
        }
    }

    private int generateRandomNumber(int bound) {
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