package br.com.jafethenrique.JavaChallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class ApplicationStart {

	public static void main(String[] args) {

		SpringApplication.run(ApplicationStart.class, args);
	}

	@PostConstruct
	void started() {
		// set JVM timezone as UTC
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
