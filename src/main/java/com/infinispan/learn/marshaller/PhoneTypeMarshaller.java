package com.infinispan.learn.marshaller;

import com.infinispan.learn.entity.PhoneType;
import org.infinispan.protostream.EnumMarshaller;
import org.springframework.stereotype.Component;

@Component
public class PhoneTypeMarshaller implements EnumMarshaller<PhoneType> {

    @Override
    public Class<? extends PhoneType> getJavaClass() {
        return PhoneType.class;
    }

    @Override
    public String getTypeName() {
        return "proto.Person.PhoneType";
    }

    @Override
    public PhoneType decode(int i) {

        return null;
    }

    @Override
    public int encode(PhoneType phoneType) throws IllegalArgumentException {
        switch (phoneType){
            case HOME:
                return 0;
            case WORK:
                return 1;
            case MOBILE:
                return 2;
        }
        throw new IllegalArgumentException("Unexpected PhoneType value : " + phoneType);
    }
}
