package com.tenic.possystem.country;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */
@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/country")
@Tag(name = "Country", description = "Countries")
@CrossOrigin
public class CountryController {
    private final CountryService countryService;

    @GetMapping("/get-country-list")
    @Operation(description = "Get list of all countries")
    public Page<Country> getAllCountries(@RequestParam(defaultValue = "0") Integer pageNumber,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return countryService.getAllCountries(pageable).getBody();
    }
}
