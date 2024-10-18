package com.banking.app.repository;

import com.banking.app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT * FROM account WHERE user_id = :userId AND is_active = TRUE", nativeQuery = true)
    List<Account> findAllActiveAccountsByUserId(@Param("userId") Long userId);


}
