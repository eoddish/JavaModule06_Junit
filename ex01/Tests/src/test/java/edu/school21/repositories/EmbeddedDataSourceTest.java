package edu.school21.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;

public class EmbeddedDataSourceTest {

    DataSource dataSource;
    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .addScript("/schema.sql")
                .addScript("/data.sql")
                .build();
    }

    @Test
    void test() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
        Assert.notNull(connection);
    }
}
