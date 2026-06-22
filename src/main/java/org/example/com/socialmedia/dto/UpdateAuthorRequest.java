package org.example.com.socialmedia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAuthorRequest {

    @Size(min = 5, message = "Full name must contain at least 5 characters")
    private String fullName;

    private String username;

    @Email(message = "Email must be valid")
    private String email;

    private String bio;
}
