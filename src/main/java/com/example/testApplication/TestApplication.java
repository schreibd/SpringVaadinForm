package com.example.testApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.testApplication.entities.Address;
import com.example.testApplication.entities.Partner;
import com.example.testApplication.repositories.AddressRepository;
import com.example.testApplication.repositories.PartnerRepository;

@SpringBootApplication
@EnableWebMvc
public class TestApplication {

	private static final Logger log = LoggerFactory.getLogger(TestApplication.class);

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(TestApplication.class, args);

		// 404 error handling via DispatcherServlet
		DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
	}

	@Bean
	public CommandLineRunner loadData(PartnerRepository repository, AddressRepository addRepo) {
		return (args) -> {
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Partner partner : repository.findAll()) {
				log.info(partner.toString());
			}
			log.info("");

			log.info("All addresses:");
			log.info("---------------------------------");
			for (Address address : addRepo.findAll()) {
				log.info(address.toString());
			}
			log.info("");
		};
	}
}
