package com.aflr.mybankbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "notice_details")
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeId;
    private String noticeSummary;
    private String noticeDetails;
    private LocalDate noticBegDt;
    private LocalDate noticEndDt;
    private LocalDate updateDt;
}
