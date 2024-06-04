package com.tenic.possystem.country;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Terrance Nyamfukudza
 * 31/5/2024
 */
@Entity
@Table(name = "country")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String flag;
    private String code;
    private Boolean enabled;
}
