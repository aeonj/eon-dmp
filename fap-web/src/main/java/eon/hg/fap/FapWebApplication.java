package eon.hg.fap;

import eon.hg.fap.core.listener.LicenseListener;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

public class FapWebApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FapWebApplication.class);
		application.addListeners(new LicenseListener());
		application.run(args);
	}

	@Bean
	public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
		return new OpenEntityManagerInViewFilter();
	}

}
