package com.tenic.possystem.useraccount;

import com.tenic.possystem.utils.enums.Status;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */
public interface UserAccountService {
    UserAccount create(UserAccountRequest request);
    UserAccount getUser(String principal);
    String changePassword(String userId);

    ResponseEntity<UserAccount> delete(Long id);
    UserAccount setApproval(Long userId);
    UserAccount deactivate(Long userId);
    List<UserAccount> getByApprovalStatus(Boolean isApproved);
    List<UserAccount> getByStatus(Status status);
}
