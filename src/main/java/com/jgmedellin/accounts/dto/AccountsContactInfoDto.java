package com.jgmedellin.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Map;
import java.util.List;

/**
 * This class represents the contact information for the Accounts microservice.
 * It is used to map the properties from the application.properties file.
 * The @ConfigurationProperties annotation is used to map the properties from the application.properties file to this class.
 * The prefix attribute is used to specify the prefix of the properties to map.
 * The record keyword is used to define a record class, which is a special type of class introduced in Java 14.
 * Records are a concise way to define classes that are mainly used to store data.
 * The record class automatically generates the constructor, getters, and toString method based on the fields defined in the record.
 */
@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(
        String message,
        Map<String, String> contactDetails,
        List<String> onCallSupport
) { }