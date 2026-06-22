package org.example.com.socialmedia.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePostRequest {

    @Size(min = 10, max = 100, message = "Title must contain between 10 and 100 characters")
    private String title;

    @Size(min = 50, max = 2000, message = "Content must contain between 50 and 2000 characters")
    private String content;
}
