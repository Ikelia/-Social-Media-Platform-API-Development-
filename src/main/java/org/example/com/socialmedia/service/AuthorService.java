package org.example.com.socialmedia.service;

import lombok.RequiredArgsConstructor;
import org.example.com.socialmedia.dto.AuthorResponse;
import org.example.com.socialmedia.dto.CreateAuthorRequest;
import org.example.com.socialmedia.dto.UpdateAuthorRequest;
import org.example.com.socialmedia.entity.Author;
import org.example.com.socialmedia.exception.DuplicateResourceException;
import org.example.com.socialmedia.exception.ResourceNotFoundException;
import org.example.com.socialmedia.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorResponse createAuthor(CreateAuthorRequest request) {
        if (authorRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken");
        }
        if (authorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email '" + request.getEmail() + "' is already registered");
        }

        Author author = Author.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .build();

        Author saved = authorRepository.save(author);
        return toResponse(saved, "Author created successfully");
    }

    @Transactional(readOnly = true)
    public AuthorResponse getAuthorById(Long id) {
        Author author = findAuthorById(id);
        return toResponse(author, "Author retrieved successfully");
    }

    @Transactional(readOnly = true)
    public AuthorResponse getAuthorByUsername(String username) {
        Author author = authorRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with username: " + username));
        return toResponse(author, "Author retrieved successfully");
    }

    @Transactional(readOnly = true)
    public AuthorResponse getAuthorByEmail(String email) {
        Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with email: " + email));
        return toResponse(author, "Author retrieved successfully");
    }

    @Transactional(readOnly = true)
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(a -> toResponse(a, "Author retrieved successfully"))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AuthorResponse> getAuthorsByPeriod(LocalDateTime from, LocalDateTime to) {
        return authorRepository.findByCreatedAtBetween(from, to)
                .stream()
                .map(a -> toResponse(a, "Author retrieved successfully"))
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorResponse updateAuthor(Long id, UpdateAuthorRequest request) {
        Author author = findAuthorById(id);

        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            author.setFullName(request.getFullName());
        }

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            if (!request.getUsername().equals(author.getUsername()) &&
                    authorRepository.existsByUsername(request.getUsername())) {
                throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken");
            }
            author.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!request.getEmail().equals(author.getEmail()) &&
                    authorRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Email '" + request.getEmail() + "' is already registered");
            }
            author.setEmail(request.getEmail());
        }

        if (request.getBio() != null) {
            author.setBio(request.getBio());
        }

        Author updated = authorRepository.save(author);
        return toResponse(updated, "Author updated successfully");
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        authorRepository.delete(author);
    }

    public Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    private AuthorResponse toResponse(Author author, String message) {
        return AuthorResponse.builder()
                .message(message)
                .fullName(author.getFullName())
                .username(author.getUsername())
                .email(author.getEmail())
                .createdAt(author.getCreatedAt())
                .build();
    }
}
