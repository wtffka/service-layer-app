package exercise.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDto {

    private String name;
    private int temperature;
    private String cloudy;
    private int wind;
    private int humidity;
}
