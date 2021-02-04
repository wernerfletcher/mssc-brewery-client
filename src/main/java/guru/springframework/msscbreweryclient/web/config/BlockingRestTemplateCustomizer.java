package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

    private final int connectionPoolMaxTotal;
    private final int connectionPoolDefaultMaxPerRoute;
    private final int connectionRequestTimeout;
    private final int requestSocketTimeout;

    public BlockingRestTemplateCustomizer(@Value("${rest.template.connectionPoolMaxTotal}") int connectionPoolMaxTotal,
                                          @Value("${rest.template.connectionPoolDefaultMaxPerRoute}") int connectionPoolDefaultMaxPerRoute,
                                          @Value("${rest.template.connectionRequestTimeout}") int connectionRequestTimeout,
                                          @Value("${rest.template.requestSocketTimeout}") int requestSocketTimeout) {
        this.connectionPoolMaxTotal = connectionPoolMaxTotal;
        this.connectionPoolDefaultMaxPerRoute = connectionPoolDefaultMaxPerRoute;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.requestSocketTimeout = requestSocketTimeout;
    }

    private ClientHttpRequestFactory requestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(connectionPoolMaxTotal);
        connectionManager.setDefaultMaxPerRoute(connectionPoolDefaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(requestSocketTimeout)
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(requestConfig)
                .build();

        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        restTemplate.setRequestFactory(requestFactory());
    }
}
