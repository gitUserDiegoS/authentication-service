package co.com.pragma.r2dbc.config;


import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.com.pragma.r2dbc.config.MySQLConnectionProperties;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;


import java.time.Duration;

@Configuration
public class R2dbcConfig {

    @Bean
    public ConnectionPool connectionPool(MySQLConnectionProperties props) {

        MySqlConnectionConfiguration dbConfig = MySqlConnectionConfiguration.builder()
                .host(props.host())
                .port(props.port())
                .username(props.username())
                .password(props.password())
                .database(props.database())
                .build();

        ConnectionPoolConfiguration poolConfig = ConnectionPoolConfiguration.builder(MySqlConnectionFactory.from(dbConfig))
                .initialSize(5)
                .maxSize(20)
                .maxIdleTime(Duration.ofMinutes(30))
                .validationQuery("SELECT 1")
                .build();

        return new ConnectionPool(poolConfig);
    }

}
