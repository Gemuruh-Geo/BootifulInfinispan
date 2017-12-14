package com.infinispan.learn.marshaller;

import com.infinispan.learn.entity.PhoneNumber;
import com.infinispan.learn.entity.PhoneType;
import org.infinispan.protostream.MessageMarshaller;

import java.io.IOException;

public class PhoneNumberMarshaller implements MessageMarshaller<PhoneNumber>{
    @Override
    public PhoneNumber readFrom(ProtoStreamReader protoStreamReader) throws IOException {
        PhoneNumber phoneNumber = new PhoneNumber();
        String number = protoStreamReader.readString("number");
        PhoneType phoneType = protoStreamReader.readEnum("phoneType",PhoneType.class);
        phoneNumber.setNumber(number);
        phoneNumber.setPhoneType(phoneType);
        return phoneNumber;
    }

    @Override
    public void writeTo(ProtoStreamWriter protoStreamWriter, PhoneNumber phoneNumber) throws IOException {
        protoStreamWriter.writeString("number",phoneNumber.getNumber());
        protoStreamWriter.writeEnum("phoneType",phoneNumber.getPhoneType(),PhoneType.class);
    }

    @Override
    public Class<? extends PhoneNumber> getJavaClass() {
        return PhoneNumber.class;
    }

    @Override
    public String getTypeName() {
        return "proto.Person.PhoneNumber";
    }
}
