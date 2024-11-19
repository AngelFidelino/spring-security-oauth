package com.aflr.mybankbackend;

import com.aflr.mybankbackend.entities.Contact;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(value = "mockuser")
public class ContactControllerTest extends CommonTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testContactSave() throws Exception {
        Contact contact = new Contact();
        contact.setContactName("angel");
        contact.setContactEmail("angel@hotmail.com");
        System.out.println("objectMapper.writeValueAsString(Set.of(contact)): " + objectMapper.writeValueAsString(
                Set.of(contact)));
        final MvcResult response = mockMvc.perform(
                        post("/contact").with(csrf()).content(objectMapper.writeValueAsString(Set.of(contact)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().isOk())
                .andReturn();

        final Set<Contact> contacts =
                objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
                });

        assertThat(contacts).extracting(Contact::getContactEmail).contains(contact.getContactEmail());

        //contact.getContactEmail().equals(contacts.get(0).getContactEmail());

        System.out.println("contacts: " + contacts);

    }
}
