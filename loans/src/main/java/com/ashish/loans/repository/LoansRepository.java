package com.ashish.loans.repository;

import com.ashish.loans.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans, Long> {

    /**
     * Finds a {@link Loans} entity by the given mobile number.
     *
     * @param mobileNumber the mobile number to search for
     * @return an {@link Optional} containing the {@link Loans} entity, or an empty {@link Optional} if no such entity exists
     */
    Optional<Loans> findByMobileNumber(String mobileNumber);
}
