package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    CustomerClient customerClient;

    @Test
    void getCustomerById() {
        CustomerDto dto = customerClient.getCustomerById(UUID.randomUUID());

        assertNotNull(dto);
        System.out.println(dto.toString());
    }

    @Test
    void saveNewCustomer() {
        CustomerDto dto = CustomerDto.builder().name("Test Customer").build();
        URI uri = customerClient.saveNewCustomer(dto);

        assertNotNull(uri);
        System.out.println(uri);
    }

    @Test
    void updateCustomer() {
        customerClient.updateCustomer(UUID.randomUUID(), CustomerDto.builder().build());
    }

    @Test
    void deleteCustomer() {
        customerClient.deleteCustomer(UUID.randomUUID());
    }
}
