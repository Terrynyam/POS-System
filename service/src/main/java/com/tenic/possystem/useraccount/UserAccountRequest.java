package com.tenic.possystem.useraccount;

import com.tenic.possystem.utils.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserAccountRequest {
    private String firstname;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Long countryId;
    @Enumerated(EnumType.STRING)
    private UserType userType;
}
