package eon.hg.fap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "eon.hg.*")
public class FapOauth2Application {

	public static void main(String[] args) {
		SpringApplication.run(FapOauth2Application.class, args);
	}

}
