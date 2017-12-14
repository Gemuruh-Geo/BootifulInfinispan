package com.infinispan.learn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootifulInfinispanApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BootifulInfinispanApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("Hello World");
	}
}
