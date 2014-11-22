/*
 * Avaya Inc. – Proprietary (Restricted)
 * Solely for authorized persons having a need to know
 * pursuant to Company instructions.
 *
 * Copyright © 2006-2014 Avaya Inc. All rights reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF Avaya Inc.
 * The copyright notice above does not evidence any actual
 * or intended publication of such source code.
 */
package com.grapeup.configs;

import com.grapeup.repositories.UserRepository;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author mcabaj
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
//        String openshiftMongoDbHost = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
//        int openshiftMongoDbPort = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
//        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
//        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");

        String openshiftMongoDbHost = "localhost";
        int openshiftMongoDbPort = 27017;
        String username = "";
        String password = "";
        Mongo mongo = new Mongo(openshiftMongoDbHost, openshiftMongoDbPort);
        UserCredentials userCredentials = new UserCredentials(username, password);
 //       String databaseName = System.getenv("OPENSHIFT_APP_NAME");
        String databaseName = "lunch";
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName, userCredentials);

        //remove _class
        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory, new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);
        return mongoTemplate;
    }
}
