package com.brashevets.carshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.comment.CarComment;

/**
 * Spring Data JPA repository for the CarComment entity.
 */
@Repository
public interface CarCommentRepository extends JpaRepository<CarComment,Long> {

    @Query("select carComment from CarComment carComment where carComment.user.login = ?#{principal.username}")
    List<CarComment> findByUserIsCurrentUser();

}
