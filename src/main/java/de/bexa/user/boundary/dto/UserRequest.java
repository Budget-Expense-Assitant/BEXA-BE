package de.bexa.user.boundary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.bexa.errorMessages.UserErrorMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotEmpty(message = UserErrorMessages.CANNOT_BE_NULL)
    @Size(min = 3, message = UserErrorMessages.USERNAME_TOO_SHORT)
    @Size(max = 20, message = UserErrorMessages.USERNAME_TOO_LONG)
    private String username;

    @NotEmpty(message = UserErrorMessages.CANNOT_BE_NULL)
    @Size(min = 3, message = UserErrorMessages.PASSWORD_TOO_SHORT)
    private String password;
    private String currentPassword;
}