import java.util.Date;

public class Weather {
    public String city;
    public String text;
    public float temperature;
    public Date date;

    Weather(String c, String t, float temp, Date d) {
        city = c;
        text = t;
        temperature = temp;
        date = d;
    }
}
