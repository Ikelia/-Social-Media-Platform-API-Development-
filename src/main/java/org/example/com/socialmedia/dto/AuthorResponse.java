package org.example.com.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponse {

    private String message;
    private String fullName;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}
