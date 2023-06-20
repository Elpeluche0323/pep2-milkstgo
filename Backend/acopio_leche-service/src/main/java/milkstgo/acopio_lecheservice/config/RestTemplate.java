package milkstgo.acopio_lecheservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplate {
    @Bean
    public org.springframework.web.client.RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
    @LoadBalanced
    public org.springframework.web.client.RestTemplate restTemplate()
    {return new org.springframework.web.client.RestTemplate();}

    public String getForObject(String proveedorUrl, Class<String> stringClass) {
    }
}