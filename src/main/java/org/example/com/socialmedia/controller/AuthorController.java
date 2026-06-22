package org.example.com.socialmedia.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.com.socialmedia.dto.AuthorResponse;
import org.example.com.socialmedia.dto.CreateAuthorRequest;
import org.example.com.socialmedia.dto.UpdateAuthorRequest;
import org.example.com.socialmedia.service.AuthorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.createAuthor(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AuthorResponse> getAuthorByUsername(@PathVariable String username) {
        return ResponseEntity.ok(authorService.getAuthorByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AuthorResponse> getAuthorByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authorService.getAuthorByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/period")
    public ResponseEntity<List<AuthorResponse>> getAuthorsByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(authorService.getAuthorsByPeriod(from, to));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAuthorRequest request) {
        return ResponseEntity.ok(authorService.updateAuthor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
