package com.tenic.possystem.embeddables;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

/**
 * @author Terrance Nyamfukudza
 * 30/5/2024
 */
@Embeddable
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Audit {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;


    public Audit() {
        setCreatedDate(LocalDateTime.now());
        setModifiedDate(LocalDateTime.now());
    }

    public Audit(LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
