package com.aflr.mybankbackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "contact_messages")
public class Contact extends BaseEntity {
    @Id
    private String contactId;
    private String contactName;
    private String contactEmail;
    private String subject;
    private String message;
}
