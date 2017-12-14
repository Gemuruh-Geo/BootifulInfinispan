package com.infinispan.learn.marshaller;

import com.infinispan.learn.entity.Person;
import com.infinispan.learn.entity.PhoneNumber;
import org.infinispan.protostream.MessageMarshaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonMarshaller implements MessageMarshaller<Person>{
    @Override
    public Person readFrom(ProtoStreamReader protoStreamReader) throws IOException {
        String name = protoStreamReader.readString("name");
        int id = protoStreamReader.readInt("id");
        List<PhoneNumber> phoneNumbers = protoStreamReader.readCollection("phoneNumbers",new ArrayList<>(),PhoneNumber.class);
        String email = protoStreamReader.readString("email");

        Person person = new Person();
        person.setEmail(email);
        person.setId(id);
        person.setName(name);
        person.setPhoneNumbers(phoneNumbers);
        return person;
    }

    @Override
    public void writeTo(ProtoStreamWriter protoStreamWriter, Person person) throws IOException {
        String name = person.getName();
        List<PhoneNumber> phoneNumbers = person.getPhoneNumbers();
        int id = person.getId();
        String email = person.getEmail();

        protoStreamWriter.writeString("name",name);
        protoStreamWriter.writeInt("id",id);
        protoStreamWriter.writeString("email",email);
        protoStreamWriter.writeCollection("phoneNumbers",phoneNumbers,PhoneNumber.class);
    }

    @Override
    public Class<? extends Person> getJavaClass() {
        return Person.class;
    }

    @Override
    public String getTypeName() {
        return "proto.Person";
    }
}
