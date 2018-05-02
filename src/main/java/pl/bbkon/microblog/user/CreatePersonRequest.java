package pl.bbkon.microblog.user;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
class CreatePersonRequest {

    @NotNull
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long")
    private String username;

    @NotNull
    @Size(min = 4, max = 100, message = "Password must be at least 4 characters long")
    private String password;

    @Column(nullable = false)
    @Email(message = "Incorrect email format")
    private String email;

}