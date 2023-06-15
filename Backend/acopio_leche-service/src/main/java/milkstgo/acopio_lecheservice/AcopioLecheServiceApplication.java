package milkstgo.acopio_lecheservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AcopioLecheServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcopioLecheServiceApplication.class, args);
	}

}
