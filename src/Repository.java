
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    String filename;

    Connection conn;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Repository(String file) throws java.sql.SQLException {
        filename = file;

        conn = DriverManager.getConnection("jdbc:sqlite:"+filename);

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS weather (" +
                "id          INTEGER not null primary key autoincrement," +
                "city        TEXT," +
                "text        TEXT," +
                "temperature REAL," +
                "date        TEXT" +
                ");");
        stmt.close();
    }

    public void Close() {
        try {
            conn.close();
        } catch (Exception ignored) {
        }
    }

    public void save(Weather weather) throws java.sql.SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO WEATHER (city, text, temperature, date) VALUES(?, ?, ?, ?)");
        stmt.setString(1, weather.city);
        stmt.setString(2, weather.text);
        stmt.setFloat(3, weather.temperature);
        stmt.setString(4, formatter.format(weather.date));
        stmt.execute();
        stmt.close();
    }

    public Weather[] load() throws java.sql.SQLException, ParseException {
        List<Weather> result = new ArrayList<>();

        PreparedStatement stmt = conn.prepareStatement("SELECT city, text, temperature, date FROM weather");
        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            Weather weather = new Weather(res.getString(1), res.getString(2), res.getFloat(3), formatter.parse(res.getString(4)));
            result.add(weather);
        }
        stmt.close();

        return result.toArray(new Weather[]{});
    }
}



