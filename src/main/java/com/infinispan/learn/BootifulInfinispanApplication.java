package com.infinispan.learn;

import com.infinispan.learn.entity.Person;
import com.infinispan.learn.service.InfinispanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BootifulInfinispanApplication implements CommandLineRunner{

    @Autowired
    private InfinispanService infinispanService;

	public static void main(String[] args) {
		SpringApplication.run(BootifulInfinispanApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
	    infinispanService.addPerson(1,"Maryam Perempuan Sophia","gemuruh.geo.pratama@gmail.com");
	    List<Person> persons = infinispanService.queryPersonByName("Maryam Perempuan Sophia");
	    persons.forEach(p->System.out.println(p.getEmail()));
	}
}
