package org.example.com.socialmedia.service;

import lombok.RequiredArgsConstructor;
import org.example.com.socialmedia.dto.CreatePostRequest;
import org.example.com.socialmedia.dto.PostResponse;
import org.example.com.socialmedia.dto.UpdatePostRequest;
import org.example.com.socialmedia.entity.Author;
import org.example.com.socialmedia.entity.Post;
import org.example.com.socialmedia.exception.ResourceNotFoundException;
import org.example.com.socialmedia.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthorService authorService;

    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        Author author = authorService.findAuthorById(request.getAuthorId());

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .visibility(request.getVisibility())
                .createdBy(author)
                .build();

        Post saved = postRepository.save(post);
        return toResponse(saved, "Post created successfully");
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(p -> toResponse(p, "Post retrieved successfully"))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByAuthor(Long authorId) {
        authorService.findAuthorById(authorId);
        return postRepository.findByCreatedById(authorId)
                .stream()
                .map(p -> toResponse(p, "Post retrieved successfully"))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = findPostById(id);
        return toResponse(post, "Post retrieved successfully");
    }

    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {
        Post post = findPostById(id);

        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            post.setTitle(request.getTitle());
        }

        if (request.getContent() != null && !request.getContent().isBlank()) {
            post.setContent(request.getContent());
        }

        Post updated = postRepository.save(post);
        return toResponse(updated, "Post updated successfully");
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = findPostById(id);
        postRepository.delete(post);
    }

    @Transactional
    public void deletePostByAuthorAndId(Long authorId, Long postId) {
        authorService.findAuthorById(authorId);
        postRepository.findByCreatedById(authorId)
                .stream()
                .filter(p -> p.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Post with id " + postId + " not found for author with id " + authorId));

        postRepository.deleteByAuthorIdAndPostId(authorId, postId);
    }

    @Transactional
    public void deletePostsByPeriod(LocalDateTime from, LocalDateTime to) {
        List<Post> posts = postRepository.findByCreatedAtBetween(from, to);
        postRepository.deleteAll(posts);
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    private PostResponse toResponse(Post post, String message) {
        return PostResponse.builder()
                .message(message)
                .title(post.getTitle())
                .content(post.getContent())
                .visibility(post.getVisibility())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy().getFullName())
                .build();
    }
}
