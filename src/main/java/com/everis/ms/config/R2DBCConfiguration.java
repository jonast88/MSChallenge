
package com.everis.ms.config;

import com.everis.ms.repository.TemperatureRepository;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.connectionfactory.init.CompositeDatabasePopulator;
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer;
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;


@Configuration
@EnableR2dbcRepositories(basePackages = "com.everis.ms.repository")
class R2DBCConfiguration extends AbstractR2dbcConfiguration {



    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.
                builder()
                .option(ConnectionFactoryOptions.DRIVER, "h2")
                //.option(ConnectionFactoryOptions.PROTOCOL, "mem")  // file, mem
                // .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.USER, "sa")
                // .option(ConnectionFactoryOptions.PASSWORD, "")
                .option(ConnectionFactoryOptions.DATABASE, "testdb")
                .build();

        return ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1");


        // return ConnectionFactories.get(options);
    }

    @Bean
    public Scheduler scheduler() {
        return Schedulers.elastic();
    }
/*
    @Bean
    public H2ConnectionFactory connectionFactoru() {
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        .inMemory("testdb")
                        .username("sa")
                        .build()
        );
    }

 */
/*
    @Bean
    public DatabaseClient databaseClient(ConnectionFactory factory) {
        return DatabaseClient.create(factory);
    }

 */
    /*
    @Bean
    public R2dbcRepositoryFactory factory(DatabaseClient client) {
        RelationalMappingContext context = new RelationalMappingContext();
        context.afterPropertiesSet();
        return new R2dbcRepositoryFactory(client, context);
    }


     */
    /*
    @Bean
    public TemperatureRepository temperatureRepository(R2dbcRepositoryFactory factory)  {
        return factory.getRepository(TemperatureRepository.class);
    }

     */

    @Bean
    public ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

}

