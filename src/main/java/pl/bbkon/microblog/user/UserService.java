package pl.bbkon.microblog.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.bbkon.microblog.role.Role;
import pl.bbkon.microblog.role.RoleEnum;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public User create(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .authorities(Collections.singletonList(new Role(RoleEnum.USER)))
                .status(User.Status.ACTIVE)
                .build();

        return repository.save(user);
    }

    public void uploadAvatar(MultipartFile avatar, Principal principal) {
        User user = repository.findByUsername(principal.getName()).get();
        try {
            user.setAvatar(avatar.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        repository.save(user);

    }
//
//
//    public boolean uploadAvatar(MultipartFile avatar, Principal principal) {
//        String filename = avatar.getOriginalFilename();
//        String directory = "C:/Repositories/microblog/src/main/resources/static/avatars";
//        String filepath = Paths.get(directory, filename).toString();
//        String username = principal.getName();
//        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
//        user.setLogo("avatars/" + filename);
//        repository.save(user);
//
//        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)))) {
//            stream.write(avatar.getBytes());
//        } catch (IOException e) {
//            return false;
//        }
//        return true;
//    }

}
