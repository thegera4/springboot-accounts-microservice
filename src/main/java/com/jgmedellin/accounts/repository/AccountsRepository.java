package com.jgmedellin.accounts.repository;

import com.jgmedellin.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

  /**
   * Fetches the account details from the DB given the customer id.
   * @param customerId Customer id number
   * @return Optional object of the account details
   */
  Optional<Accounts> findByCustomerId(Long customerId);

  /**
   * Deletes the account from the DB given the customer id.
   * @param customerId Customer id number
   */
  @Transactional // To perform a transaction in the DB, we need to use @Transactional annotation
  @Modifying // To perform a modification in the DB, we need to use @Modifying annotation
  void deleteByCustomerId(Long customerId);

}