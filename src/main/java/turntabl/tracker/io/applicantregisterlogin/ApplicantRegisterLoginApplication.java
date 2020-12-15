package turntabl.tracker.io.applicantregisterlogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ApplicantRegisterLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicantRegisterLoginApplication.class, args);
	}

}
