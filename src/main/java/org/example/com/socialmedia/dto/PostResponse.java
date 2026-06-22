package org.example.com.socialmedia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.com.socialmedia.entity.Visibility;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {

    private String message;
    private String title;
    private String content;
    private Visibility visibility;
    private LocalDateTime createdAt;
    private String createdBy;
}
