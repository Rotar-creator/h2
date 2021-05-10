import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class H2DbemR {
    private static final String URL_MEM = "jdbc:h2:mem:h2db";
    private static final String USER = "sa";
    private static final String PASSWD = "sa";
    private static Connection connection;

    @SneakyThrows
    public static void main(String[] args) {
        Map<Integer, String> idToNameMap = new HashMap<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("select name from animal")) {
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            System.out.println(e.getErrorCode());
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

