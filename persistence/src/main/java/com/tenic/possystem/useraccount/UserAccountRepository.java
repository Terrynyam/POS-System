package com.tenic.possystem.useraccount;

import com.tenic.possystem.utils.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findUserAccountByEmail(String email);
    Optional<UserAccount> findByUsername(String username);
    List<UserAccount> findByIsApproved (Boolean isApproved);
    List<UserAccount> findByStatus(Status status);
}
