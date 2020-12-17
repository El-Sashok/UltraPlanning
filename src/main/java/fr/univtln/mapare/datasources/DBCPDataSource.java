package fr.univtln.mapare.datasources;

import fr.univtln.mapare.App;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setUrl(App.getProperty("db.url"));
        ds.setUsername(App.getProperty("db.username"));
        ds.setPassword(App.getProperty("db.password"));

        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    private DBCPDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}