package sapo.intern.mock.carstore;

import com.cloudinary.Cloudinary;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class CarstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarstoreApplication.class, args);
	}

	@Bean
	public Cloudinary cloudinaryConfig() {
		Cloudinary cloudinary = null;
		Map config = new HashMap();
		config.put("cloud_name", "dzo94imrh");
		config.put("api_key", "245844245726586");
		config.put("api_secret", "gnMQPlIyKkGQPXXLBl1XqQ-sBq4");
		cloudinary = new Cloudinary(config);
		return cloudinary;
	}


}
