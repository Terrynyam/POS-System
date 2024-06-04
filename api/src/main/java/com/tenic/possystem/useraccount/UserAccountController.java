package com.tenic.possystem.useraccount;

import com.tenic.possystem.utils.enums.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */
@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/user-account")
@Tag(name = "User Account", description = "User Account")
@CrossOrigin
@SecurityRequirement(name = "authorization")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/create")
    @Operation(description = "Add new user ")
    public UserAccount addUser(@RequestBody UserAccountRequest request) {
        return userAccountService.create(request);
    }
    @Operation(description = "Retrieves user details ")
    @GetMapping("/user-details")
    public UserAccount getUserDetails(Principal principal) {
        return userAccountService.getUser(principal.getName());
    }

    @PutMapping("/{userId}/approve")
    @Operation(description = "Approve User account")
    public ResponseEntity<UserAccount> approveUserAccount(@PathVariable Long userId) {
        return ResponseEntity.ok(userAccountService.setApproval(userId));
    }
    @Operation(description = "Retrieves Users by approval status")
    @GetMapping("/get/{isApproved}")
    public List<UserAccount> getByApprovalStatus(@PathVariable Boolean isApproved) {
        return userAccountService.getByApprovalStatus(isApproved);
    }
    @Operation(description = "Retrieves Users by status")
    @GetMapping("/get/status/")
    public List<UserAccount> getByRegistrationStatus(@RequestParam(name = "activation-status")
                                                         Status status) {
        return userAccountService.getByStatus(status);
    }
    @PutMapping("/{userId}/deactivate")
    @Operation(description = "Deactivate User account")
    public ResponseEntity<UserAccount> deactivateUserAccount(@PathVariable Long userId) {
        return ResponseEntity.ok(userAccountService.deactivate(userId));
    }
}
