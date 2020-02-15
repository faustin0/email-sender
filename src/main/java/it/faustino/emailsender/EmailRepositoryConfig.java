package it.faustino.emailsender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@EnableJdbcRepositories
@Configuration
public class EmailRepositoryConfig extends AbstractJdbcConfiguration {

    @Bean
    @Profile("local")
    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder()
                .setScriptEncoding("UTF-8")
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true)
                .addScript("sql/create-email-schema.sql")
                .build();
    }


}
