package com.aflr.mybankbackend;

import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends CommonTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(value = "mockuser", roles = "ADMIN")
    void testCustomerSave() throws Exception {
        Customer customer = new Customer();
        customer.setName("Angel");
        customer.setEmail("angel@hotmail.com");
        customer.setMobileNumber("7777129832");

        String body = objectMapper.writeValueAsString(customer);
        mockMvc.perform(post("/customer").with(csrf()).content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcResultHandlers.log())
                .andExpect(status().isOk())
                .andReturn();

        final MvcResult response = mockMvc.perform(get("/customer")).andExpect(status().isOk()).andReturn();
        final String responseAsString = response.getResponse().getContentAsString();

        System.out.println("responseAsString: " + responseAsString);
        assertThat(responseAsString).contains(customer.getEmail());

    }

}
