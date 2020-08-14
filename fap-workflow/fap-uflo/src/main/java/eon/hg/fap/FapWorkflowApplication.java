package eon.hg.fap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan(basePackages = "eon.hg.*")
public class FapWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(FapWorkflowApplication.class, args);
    }

}
