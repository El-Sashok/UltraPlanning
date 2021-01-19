package fr.univtln.mapare.datasources;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        //ds.setUrl("jdbc:h2:tcp://siannos.fr:9092/~/h2/database");
        //ds.setUrl("jdbc:h2:D:/Coding/UltraPlanning/database");
        ds.setUrl("jdbc:h2:" + System.getProperty("user.dir") + "/database");
        ds.setUsername("root");
        ds.setPassword("toor");

        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    private DBCPDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

//    public static void main(String[] args) throws SQLException {
//        try (Connection con = getConnection();
//             java.sql.Statement stmt = con.createStatement()) {
//            stmt.executeQuery(String.format("SCRIPT TO '%s'", (System.getProperty("user.dir") + "/Scriptdepeuplement.sql")));
//        }
//    }
}

