package com.brashevets.carshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.brashevets.carshop.model.DealerFeedback;

/**
 * Spring Data JPA repository for the DealerFeedback entity.
 */
@Repository
public interface DealerFeedbackRepository extends JpaRepository<DealerFeedback,Long> {

    @Query("select dealerFeedback from DealerFeedback dealerFeedback where dealerFeedback.user.login = ?#{principal.username}")
    List<DealerFeedback> findByUserIsCurrentUser();

}
