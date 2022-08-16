import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e ) {
            System.out.println(e);
            return;
        }


        Repository repo;
        try {
            repo = new Repository("weather.db");
        } catch (SQLException e) {
            System.out.println(e);
            return;
        }

        WeatherResponse forecast = new WeatherResponse();

        Scanner scan = new Scanner(System.in);
        String next = "";
        info();
        while (!next.equals("q")) {
            next = scan.nextLine();

            switch (next) {
                case "1" :
//      Сохранение текущего прогноза погоды в базу данных
                    forecast.load();
                    try {
                        repo.save(forecast.get_weather());
                    } catch (Exception e) {
                        System.out.println(e);
                        repo.Close();
                        return;
                    }
                    info();
                    break;

                case "2" :
//      Отображение прогноза погоды из базы данных
                    try {
                        Weather[] weathers = repo.load();
                        for (Weather w : weathers) {
                            System.out.println("|В городе " + w.city + " на дату " + w.date + " ожидается " + w.text + ", температура " + w.temperature + "°C|");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        repo.Close();
                        return;
                    }
                    info();
                    break;

            }
        }

        repo.Close();
    }

    public static void info() {
        System.out.println("1: прогноз на сегодня");
        System.out.println("2: прогноз из базы данных");
        System.out.println("q: выход");
    }

}
