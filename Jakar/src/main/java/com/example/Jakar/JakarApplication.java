package com.example.Jakar;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import java.util.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JakarApplication {

	public static void main(String[] args) {
		SpringApplication.run(JakarApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner printBeans(ApplicationContext context) {
//		return args -> {
//			System.out.println("\nRegistered Beans in ApplicationContext:");
//			Arrays.stream(context.getBeanDefinitionNames())
//					.sorted()
//					.forEach(System.out::println);
//		};
//	}
}
