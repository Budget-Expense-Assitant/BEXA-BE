package de.bexa.user.boundary.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    private String userName;
    private String password;
}