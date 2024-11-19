package com.aflr.mybankbackend;

import com.aflr.mybankbackend.entities.Accounts;
import com.aflr.mybankbackend.entities.Customer;
import com.aflr.mybankbackend.repositories.AccountsRepository;
import com.aflr.mybankbackend.repositories.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithMockUser(value = "mockuser")
class AccountControllerTest extends CommonTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testAccountSaving() throws Exception {
        Customer customer = new Customer();
        customer.setName("Angel");
        customer.setEmail("angel@hotmail.com");
        customer.setMobileNumber("7777129832");
        customer = customerRepository.save(customer);

        Accounts account = new Accounts();
        account.setAccountType("T");
        account.setCustomerId(customer.getId());
        account.setBranchAddress("St california");
        accountsRepository.save(account);

        final MvcResult response = mockMvc.perform(
                        get("/myAccount").queryParam("email", "angel@hotmail.com").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        final String responseAsString = response.getResponse().getContentAsString();
        System.out.println("responseAsString: " + responseAsString);

        Assertions.assertThat(responseAsString).contains(account.getBranchAddress());
    }

}
