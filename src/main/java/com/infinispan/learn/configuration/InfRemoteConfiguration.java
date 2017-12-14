package com.infinispan.learn.configuration;

import com.infinispan.learn.marshaller.PersonMarshaller;
import com.infinispan.learn.marshaller.PhoneNumberMarshaller;
import com.infinispan.learn.marshaller.PhoneTypeMarshaller;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.SerializationContext;
import org.infinispan.query.remote.client.ProtobufMetadataManagerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.*;

@Configuration
public class InfRemoteConfiguration {
    private RemoteCacheManager remoteCacheManager;
    private RemoteCache<Integer,Object> remoteCache;
    private static final String PROTOBUF_DEFINITION_RESOURCE = "/proto/person.proto";
    @Autowired private PersonMarshaller personMarshaller;
    @Autowired private PhoneNumberMarshaller phoneNumberMarshaller;
    @Autowired private PhoneTypeMarshaller phoneTypeMarshaller;

    @PostConstruct
    public void init() throws IOException {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .addServer()
                .host("localhost")
                .port(11222)
                .marshaller(new ProtoStreamMarshaller());

        remoteCacheManager = new RemoteCacheManager(configurationBuilder.build());
        remoteCache = remoteCacheManager.getCache();
        registerMarshaller();

    }

    @Bean
    public RemoteCache<Integer,Object> remoteCache(){
        return remoteCache;
    }

    private void registerMarshaller() throws IOException {
        SerializationContext context = ProtoStreamMarshaller.getSerializationContext(remoteCacheManager);
        context.registerProtoFiles(FileDescriptorSource.fromResources(PROTOBUF_DEFINITION_RESOURCE));
        context.registerMarshaller(personMarshaller);
        context.registerMarshaller(phoneNumberMarshaller);
        context.registerMarshaller(phoneTypeMarshaller);

        RemoteCache<String,String> metadataCache = remoteCacheManager.getCache(ProtobufMetadataManagerConstants.PROTOBUF_METADATA_CACHE_NAME);
        metadataCache.put(PROTOBUF_DEFINITION_RESOURCE, readResource(PROTOBUF_DEFINITION_RESOURCE));
        String errors = metadataCache.get(ProtobufMetadataManagerConstants.ERRORS_KEY_SUFFIX);
        if (errors != null) {
            throw new IllegalStateException("Some Protobuf schema files contain errors:\n" + errors);
        }

    }

    private String readResource(String resourcePath) throws IOException {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        try {
            final Reader reader = new InputStreamReader(is, "UTF-8");
            StringWriter writer = new StringWriter();
            char[] buf = new char[1024];
            int len;
            while ((len = reader.read(buf)) != -1) {
                writer.write(buf, 0, len);
            }
            return writer.toString();
        } finally {
            is.close();
        }
    }

}
