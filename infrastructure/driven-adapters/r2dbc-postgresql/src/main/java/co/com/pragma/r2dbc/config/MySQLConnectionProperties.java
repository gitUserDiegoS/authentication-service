package co.com.pragma.r2dbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "spring.r2dbc")
public record MySQLConnectionProperties(
        String host,
        Integer port,
        String database,
        String username,
        String password){

        }
