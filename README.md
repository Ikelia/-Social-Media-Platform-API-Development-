File	                                     Role
Author.java / Post.java	                   JPA entities — map to DB tables. @PrePersist auto-sets createdAt. Visibility is an enum stored as a string.
CreateAuthorRequest / CreatePostRequest	   What the client sends to CREATE. Carry @Valid constraints.
UpdateAuthorRequest / UpdatePostRequest	   What the client sends to UPDATE. All fields optional, validated only when present.
AuthorResponse / PostResponse	             What the API sends back. Never exposes the raw entity. Includes a message field.
AuthorRepository / PostRepository	         Spring Data JPA interfaces — Spring generates the SQL from method names like findByUsername.
AuthorService / PostService	               Business logic lives here — uniqueness checks, partial updates, cascade deletes.
AuthorController / PostController	         HTTP layer only — maps URLs to service calls, handles status codes.
GlobalExceptionHandler	                   Catches ResourceNotFoundException → 404, DuplicateResourceException → 409, validation errors → 400 with field-level messages.
