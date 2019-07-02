package eon.hg.fap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FapHessianApplication {

	public static void main(String[] args) {
		SpringApplication.run(FapHessianApplication.class, args);
	}

}
