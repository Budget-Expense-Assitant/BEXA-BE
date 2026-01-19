package de.bexa.user.entity;

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

    private String userName;
    private String password;
    private Date createdAt;
}
