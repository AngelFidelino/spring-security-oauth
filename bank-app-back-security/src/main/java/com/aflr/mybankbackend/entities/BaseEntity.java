package com.aflr.mybankbackend.entities;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createDt;
}
