package com.infinispan.learn.service;

import com.infinispan.learn.entity.Person;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InfinispanService {
    @Autowired
    private RemoteCache<Integer,Object> remoteCache;


    public void addPerson(int id, String name, String email) {

        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setEmail(email);

        if (remoteCache.containsKey(person.getId())) {
            System.out.println("Updating person with id " + person.getId());
        }

        // put the Person in cache
        remoteCache.put(person.getId(), person);
    }


    public List<Person> queryPersonByName(String namePattern) {


        QueryFactory qf = Search.getQueryFactory(remoteCache);
        Query query = qf.from(Person.class)
                .having("name").like(namePattern).toBuilder()
                .build();

        List<Person> results = query.list();
        System.out.println("Found " + results.size() + " matches:");
        for (Person p : results) {
            System.out.println(">> " + p);
        }
        return results;
    }
}
