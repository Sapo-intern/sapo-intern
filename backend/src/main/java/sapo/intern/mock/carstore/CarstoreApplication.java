package sapo.intern.mock.carstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CarstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarstoreApplication.class, args);
	}

}
