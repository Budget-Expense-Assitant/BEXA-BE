package de.bexa.user.entity;

import de.bexa.errorMessages.UserErrorMessages;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Size(min = 3, message = UserErrorMessages.USERNAME_TOO_SHORT)
    @Size(max = 20, message = UserErrorMessages.USERNAME_TOO_LONG)
    private String userName;

    @Size(min = 3, message = UserErrorMessages.PASSWORD_TOO_SHORT)
    private String password;
    private Date createdAt;
}
