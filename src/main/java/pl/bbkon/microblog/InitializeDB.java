package pl.bbkon.microblog;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bbkon.microblog.comment.Comment;
import pl.bbkon.microblog.comment.CommentRepository;
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

    private static final String DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam convallis molestie augue vel porttitor. Suspendisse bibendum vel justo vel mollis. Suspendisse faucibus ex nisi, in tincidunt odio tristique vitae. Proin consequat dapibus congue. Aenean nec gravida dui. Praesent sed scelerisque risus. Suspendisse potenti. Nulla venenatis neque non lorem finibus faucibus. Sed mi sapien, elementum quis est non, viverra malesuada quam. Ut convallis ipsum eu arcu luctus egestas. Aenean eget est convallis, efficitur felis vitae, molestie ex. Mauris molestie sem ac consectetur pulvinar. Phasellus ligula est, pretium in neque et, bibendum bibendum enim. Aliquam tincidunt lacus felis, et consequat eros suscipit ac.";
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private EntryRepository entryRepository;
    private PasswordEncoder encoder;

    @PostConstruct
    public void fillUpDB() {
        createUsers();
        createEntries();
    }

    private void createUsers() {
        userRepository.save(createAdmin());
        userRepository.save(createUser());
    }

    private User createAdmin() {
        return User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .email("admin@admin.o2.pl")
                .status(User.Status.ACTIVE)
                .authorities(Collections.singletonList(new Role(RoleEnum.ADMIN)))
                .description(DESCRIPTION)
                .build();
    }

    private User createUser() {
        return User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .email("user@o2.pl")
                .status(User.Status.ACTIVE)
                .authorities(Collections.singletonList(new Role(RoleEnum.USER)))
                .description(DESCRIPTION)
                .build();
    }

    private void createEntries() {
        List<String> entryContentsList = createListOfEntryContents();
        List<String> commentContentsList = createListOfCommentContents();

        for (int i = 0; i < 20; i++) {
            int num = getRandomNumber(entryContentsList.size());
            entryRepository.save(Entry.builder()
                    .author(userRepository.getOne(i % 2 + 1))
                    .contents(entryContentsList.get(num))
                    .status(Entry.Status.ORIGINAL)
                    .build());
        }

        for (int i = 0; i < 60; i++) {
            int num = getRandomNumber(commentContentsList.size());
            commentRepository.save(Comment.builder()
                    .author(userRepository.getOne(i % 2 + 1))
                    .contents(commentContentsList.get(num))
                    .status(Comment.Status.ORIGINAL)
                    .entry(entryRepository.getOne(i / 10))
                    .build());
        }


    }

    private List<String> createListOfCommentContents() {
        List<String> contentsList = new ArrayList<>();
        contentsList.add("Zwariowany Marcin prowadzi 12 H live na, którym 3zł = 3 min dłużej. Serio ?");
        contentsList.add("Odwołałem się do filmu przysięga małżeńska. Naciągnąłem trochę pod temat ale może nie zauważą xD");
        contentsList.add("Ja się odwołałem do pierwszej części Dziadów i do Botoksu Patryka Vegi, ujdzie?");
        contentsList.add("Niby taki piąteczek, a jednak poniedziałek. Za raz usnę, a jeszcze 3h ");
        contentsList.add("Widzę, że u #rafatus po staremu - kolejna próba wyjścia na prostą i kolejny raz to samo. Ale to przecież wina Marleny, to ona miała na niego zły wpływ... ");
        contentsList.add("Pierwszy win w nowym sezonie, o tak ");
        contentsList.add("Wybiera się ktoś z Mirków na Trzy Korony w sobotę? (05.05)");
        return contentsList;
    }

    private int getRandomNumber(int bound) {
        Random r = new Random();
        return r.nextInt(bound);
    }

    private List<String> createListOfEntryContents() {
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