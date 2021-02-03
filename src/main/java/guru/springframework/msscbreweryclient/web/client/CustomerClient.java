package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties("sfg.brewery")
public class CustomerClient {

    private String apiHost;
    public static final String CUSTOMER_PATH_V1 = "/api/v1/customer/";
    private final RestTemplate restTemplate;

    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDto getCustomerById(UUID id) {
        return restTemplate.getForObject(String.format("%s%s%s", apiHost, CUSTOMER_PATH_V1, id), CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto dto) {
        return restTemplate.postForLocation(String.format("%s%s", apiHost, CUSTOMER_PATH_V1), dto);
    }

    public void updateCustomer(UUID id, CustomerDto dto) {
        restTemplate.put(String.format("%s%s%s", apiHost, CUSTOMER_PATH_V1, id), dto);
    }

    public void deleteCustomer(UUID id) {
        restTemplate.delete(String.format("%s%s%s", apiHost, CUSTOMER_PATH_V1, id));
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

}
