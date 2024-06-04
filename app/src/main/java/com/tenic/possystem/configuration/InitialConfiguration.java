package com.tenic.possystem.configuration;

import com.tenic.possystem.country.CountryRepository;
import com.tenic.possystem.country.CountryService;
import com.tenic.possystem.useraccount.UserAccount;
import com.tenic.possystem.useraccount.UserAccountRepository;
import com.tenic.possystem.utils.enums.Status;
import com.tenic.possystem.utils.enums.UserType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@Configuration
public class InitialConfiguration {
    @Bean
    public CommandLineRunner loadCountries(CountryRepository repository, CountryService countryService) {
        return args -> {
            if (repository.findAll().isEmpty()) {
                countryService.countryList();
            }
        };
    }
    @Bean
    public CommandLineRunner loadGlAccounts(UserAccountRepository repository) {
        return args -> {
            UserAccount admin = repository.findUserAccountByEmail("terrynyamz93@gmail.com").orElse(new UserAccount());
            admin.setFirstName("Terrance");
            admin.setLastName("Nyamfukudza");
            admin.setEmail("terrynyamz93@gmail.com");
            admin.setUserType(UserType.ADMIN);
            admin.setIsApproved(Boolean.TRUE);
            admin.setStatus(Status.ACTIVE);
            admin.setUsername("admin");
            repository.save(admin);

        };
    }
}
