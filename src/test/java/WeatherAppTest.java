import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class WeatherAppTest {

    WeatherApp weatherApp;

    @BeforeEach
    public void setup(){
        //Instantiating so we don't have to do it in each test
        weatherApp = new WeatherApp();
    }

    @Test
    @DisplayName("Should not have numerical input as city name")
    public void shouldBeAlphabet() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            weatherApp.jsonToMap("4245");
        });
    }

    @Test
    @DisplayName("Should not be null")
    public void shouldNotBeNull() {
        Throwable exception = assertThrows(NullPointerException.class, () -> weatherApp.checkForInput(null));
        Assertions.assertEquals(null, exception.getMessage());
    }
}