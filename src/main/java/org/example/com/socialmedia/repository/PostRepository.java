package org.example.com.socialmedia.repository;

import org.example.com.socialmedia.entity.Author;
import org.example.com.socialmedia.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCreatedBy(Author author);

    List<Post> findByCreatedById(Long authorId);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.createdBy.id = :authorId AND p.id = :postId")
    void deleteByAuthorIdAndPostId(@Param("authorId") Long authorId, @Param("postId") Long postId);

    List<Post> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}
