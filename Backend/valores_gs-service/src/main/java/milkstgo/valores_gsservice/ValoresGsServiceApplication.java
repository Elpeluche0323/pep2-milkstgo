package milkstgo.valores_gsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ValoresGsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValoresGsServiceApplication.class, args);
	}

}
