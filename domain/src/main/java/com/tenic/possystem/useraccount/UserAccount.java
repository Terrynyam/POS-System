package com.tenic.possystem.useraccount;

import com.tenic.possystem.country.Country;
import com.tenic.possystem.embeddables.Audit;
import com.tenic.possystem.utils.AppAuditEventListener;
import com.tenic.possystem.utils.enums.Status;
import com.tenic.possystem.utils.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */
@Entity
@Table(name = "user_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@EntityListeners(AppAuditEventListener.class)
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Boolean isApproved;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Country country;
    @Embedded
    private Audit audit;
}
