package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {

    private String apiHost;
    public final String BEER_PATH_V1 = "/api/v1/beer/";
    private final RestTemplate restTemplate;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public BeerDto getBeerById(UUID id) {
        return restTemplate.getForObject(String.format("%s%s%s", apiHost, BEER_PATH_V1, id), BeerDto.class);
    }

    public URI saveNewBeer(BeerDto dto) {
        return restTemplate.postForLocation(String.format("%s%s", apiHost, BEER_PATH_V1), dto);
    }

    public void updateBeer(UUID id, BeerDto dto) {
        restTemplate.put(String.format("%s%s%s", apiHost, BEER_PATH_V1, id), dto);
    }

    public void deleteBeerById(UUID id) {
        restTemplate.delete(String.format("%s%s%s", apiHost, BEER_PATH_V1, id));
    }

}
