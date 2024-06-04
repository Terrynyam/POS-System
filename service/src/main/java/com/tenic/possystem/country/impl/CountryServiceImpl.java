package com.tenic.possystem.country.impl;

import com.tenic.possystem.country.Country;
import com.tenic.possystem.country.CountryRepository;
import com.tenic.possystem.country.CountryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    @Override
    public void countryList() {
        Arrays.stream(Locale.getISOCountries()).forEach(locale -> {
            Locale obj = new Locale("", locale);
            countryRepository.save(Country
                    .builder()
                    .name(obj.getDisplayCountry())
                    .code(obj.getCountry())
                    .flag(obj.getCountry())
                    .enabled(Boolean.TRUE)
                    .build());
        });
    }

    @Override
    public ResponseEntity<Page<Country>> getAllCountries(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(countryRepository.findAll(pageable));
    }
}
