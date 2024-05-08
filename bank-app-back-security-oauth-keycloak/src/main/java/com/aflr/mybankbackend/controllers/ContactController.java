package com.aflr.mybankbackend.controllers;

import com.aflr.mybankbackend.entities.Contact;
import com.aflr.mybankbackend.repositories.ContactRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class ContactController {

    private ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostFilter("filterObject.contactName!='test'")
    @PostMapping("/contact")
    public Set<Contact> saveContactInquiryDetails(@RequestBody Set<Contact> contacts) {
        Set<Contact> savedContacts = new HashSet<>();
        contacts.forEach(contact->{
            contact.setContactId("SR" + ThreadLocalRandom.current().nextInt(0, 9999));
            contact.setCreateDt(LocalDateTime.now());
            savedContacts.add(contactRepository.save(contact));
        });
        return savedContacts;
    }

}
