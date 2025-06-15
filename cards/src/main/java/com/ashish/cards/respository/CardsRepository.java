package com.ashish.cards.respository;

import com.ashish.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

    /**
     * Finds a {@link Cards} entity by the given mobile number.
     *
     * @param mobileNumber the mobile number to search for
     * @return an {@link Optional} containing the {@link Cards} entity, or an empty {@link Optional} if no such entity exists
     */
    Optional<Cards> findByMobileNumber(String mobileNumber);


}
