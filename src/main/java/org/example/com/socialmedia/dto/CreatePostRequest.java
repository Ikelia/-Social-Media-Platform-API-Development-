package org.example.com.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.com.socialmedia.entity.Visibility;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePostRequest {

    @NotBlank(message = "Title must not be blank")
    @Size(min = 10, max = 100, message = "Title must contain between 10 and 100 characters")
    private String title;

    @NotBlank(message = "Content must not be blank")
    @Size(min = 50, max = 2000, message = "Content must contain between 50 and 2000 characters")
    private String content;

    @NotNull(message = "Visibility must not be null")
    private Visibility visibility;

    @NotNull(message = "Author ID must not be null")
    private Long authorId;
}
