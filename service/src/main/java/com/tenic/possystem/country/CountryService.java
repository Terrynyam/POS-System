package com.tenic.possystem.country;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
public interface CountryService {
    void countryList();

    ResponseEntity<Page<Country>> getAllCountries(Pageable pageable);
}
