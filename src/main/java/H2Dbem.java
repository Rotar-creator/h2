import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class H2Dbem {
    private static final String URL_MEM = "jdbc:h2:mem:h2db";
    private static final String USER = "sa";
    private static final String PASSWD = "sa";
    private static Connection connection;

    @SneakyThrows
    public static void main(String[] args) {
        Map<Integer, String> idToNameMap = new HashMap<>();
        try (Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate("CREATE TABLE species ( "
                    + "id INTEGER PRIMARY KEY, "
                    + "name VARCHAR(255), "
                    + "num_acres DECIMAL)");
            stmt.executeUpdate("CREATE TABLE animal ( "
                    + "id INTEGER PRIMARY KEY, "
                    + "species_id integer, "
                    + "name VARCHAR(255), "
                    + "date_born TIMESTAMP)");
            stmt.executeUpdate("INSERT INTO species VALUES (1, 'African Elephant', 7.5)");
            stmt.executeUpdate("INSERT INTO species VALUES (2, 'Zebra', 1.2)");
            stmt.executeUpdate("INSERT INTO animal VALUES (1, 1, 'Elsa', '2001-05-06T02:15')");
            stmt.executeUpdate("INSERT INTO animal VALUES (2, 2, 'Zelda', '2002-08-15T09:12')");
            stmt.executeUpdate("INSERT INTO animal VALUES (3, 1, 'Ester', '2002-09-09T10:36')");
            stmt.executeUpdate("INSERT INTO animal VALUES (4, 1, 'Eddie', '2010-06-08T01:24')");
            stmt.executeUpdate("INSERT INTO animal VALUES (5, 2, 'Zoe', '2005-11-12T03:44')");

        }
        try (Statement stmt = getConnection().createStatement();

             ResultSet rs = stmt.executeQuery("select species_id, name, date_born from animal")) {
            while (rs.next()) {
                int id = rs.getInt("species_id");
                String name = rs.getString("name");
                java.sql.Time sqlTime = rs.getTime("date_born");
                LocalTime LocalTime = sqlTime.toLocalTime();

                idToNameMap.put(id, name);
               // System.out.println(rs.getString(1));
               // System.out.println(rs.getString(2));
                System.out.println(idToNameMap);
            }
        }
        connection.close();
    }

    @SneakyThrows
    static Connection getConnection() {
        connection = connection == null ? DriverManager.getConnection(URL_MEM, USER, PASSWD) : connection;
        return connection;
    }
}
