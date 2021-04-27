import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class WeatherApp {

    /* TypeToken the class is public but its default constructor is protected, in order to instantiate the object we extended it as an anonymous inner class: new TypeToken<...>() {}
    the TypeToken part is necessary when there are Generics involved (not primitive data types) */
    public static Map<String, Object> jsonToMap(String string) {
        Map<String, Object> map = new Gson().fromJson(
                string, new TypeToken<HashMap<String, Object>>() {}.getType()
                );
        return map;
    }

    public static void checkForInput(Scanner scanner) {
        scanner.useDelimiter("\\r?\\n");
        Pattern pattern = Pattern.compile("[A-Za-z]*");
        while (scanner.hasNextLine()) {
            if (!scanner.hasNext(pattern)) {
                System.out.println("Input can only be letters!");
                scanner.nextLine(); //move the scanner on to the next line otherwise it will keep looping the sout
            } else{
                System.out.println("City accepted");
                break;
            }
        }
    }

    public static void checkMetricOrImperial(Scanner scanner) {
        scanner.useDelimiter("\\r?\\n");
        while (scanner.hasNextLine()) {
            if (scanner.hasNext("metric") || scanner.hasNext("imperial")){
                System.out.println("Units accepted");
                break;
            } else {
                System.out.println("Please enter only metric or imperial");
                scanner.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to Java OpenWeatherMap Application");
            System.out.println("Please type below your city name:");
            Scanner userInput = new Scanner(System.in);
            checkForInput(userInput);
            String cityName = userInput.nextLine();
            //This will keep going into metric regardless if city is correct or not as long as it is an alphabetical string

            System.out.println("Please enter metric or imperial");
            checkMetricOrImperial(userInput);
            String units = userInput.nextLine();

            String apiKey = "ce07b77bef389e5b67753f7a89a06e4f";
            String apiLink = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey + "&units=" + units;

            try {
                StringBuffer stringBuffer = new StringBuffer();
                // checking if link contains any spaces
                if(apiLink.contains(" "))
                    apiLink = apiLink.replace(" ", "");
                // Establishing URL connection
                URL url = new URL(apiLink);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                bufferedReader.close();

                Map<String, Object> responseMap = jsonToMap(stringBuffer.toString()); //storing response processed from stringBuffer in a Map
                Map<String, Object> mainMap = jsonToMap(responseMap.get("main").toString()); // storing main body from response in its own map
                Map<String, Object> windMap = jsonToMap(responseMap.get("wind").toString()); // storing wind body from response in its own map

                System.out.println("Current Temperature: " + mainMap.get("temp"));
                System.out.println("Current Humidity: " + mainMap.get("humidity"));
                System.out.println("Wind Speeds: " + windMap.get("speed"));
                System.out.println("Wind Angle:" + windMap.get("deg"));
                System.out.println("===================================");
                System.out.println("===================================");
            } catch (IOException ioException) {
                System.out.println(ioException);
            }
        }
    }
}
