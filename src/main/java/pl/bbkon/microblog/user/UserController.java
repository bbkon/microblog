package pl.bbkon.microblog.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private HttpServletRequest request;

    @GetMapping("/unauth/credentials")
    public String getCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String result = authentication.getName() + ", roles: ";
        result += authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .reduce("", (a, b) -> a + ", " + b);
        return result;
    }

    @PostMapping("/auth/login")
    public ResponseEntity login() {
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/auth/login")
    public ResponseEntity checkLogin() {
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/auth/service/username")
    public ResponseEntity<String> getUsername(Principal principal) {
        return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
    }

    @PostMapping(value = "/unauth/register")
    public ResponseEntity<User> register(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @GetMapping("/auth/profile")
    public ResponseEntity<User> viewLoggedInProfile(Principal principal) {
        return new ResponseEntity<>((User) userService.loadUserByUsername(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/auth/{username}/profile")
    public ResponseEntity<User> viewOtherUserProfile(@PathVariable("username") String username) {
        return new ResponseEntity<>((User) userService.loadUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/auth/avatar")
    public ResponseEntity uploadAvatar(@RequestParam("uploadfile") MultipartFile avatar) {
        String filename = avatar.getOriginalFilename();
        String directory = "C:/Repositories/microblog/src/main/resources/static/avatars";
        String filepath = Paths.get(directory, filename).toString();

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)))) {
            stream.write(avatar.getBytes());
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}