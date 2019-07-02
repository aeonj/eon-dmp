package eon.hg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "eon.hg.*")
public class FapClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FapClientApplication.class, args);
	}

}
