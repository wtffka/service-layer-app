package exercise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import exercise.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
public class WeatherService {

    @Autowired
    CityRepository cityRepository;

    // Клиент
//    HttpClient client;

    // При создании класса сервиса клиент передаётся снаружи
    // В теории это позволит заменить клиент без изменения самого сервиса
//    WeatherService(HttpClient client) {
//        this.client = client;
//    }

    // BEGIN
    public static WeatherDto getWeatherForOneCity(String city) throws JsonProcessingException {
        HttpClient client = new HttpClient();
        String weatherAsString = client.get("http://weather/api/v2/cities/" + city);
        WeatherDto weatherDto = new ObjectMapper().readValue(weatherAsString, WeatherDto.class);
        return weatherDto;
    }

    public static List<ShortWeatherDto> getWeatherForCities(List<String> cities) throws JsonProcessingException {
        HttpClient client = new HttpClient();
        List<ShortWeatherDto> shorties = new ArrayList<>();
        for(String city : cities) {
            ShortWeatherDto shortWeatherDto = new ShortWeatherDto();
            String weatherAsString = client.get("http://weather/api/v2/cities/" + city);
            WeatherDto weatherDto = new ObjectMapper().readValue(weatherAsString, WeatherDto.class);
            shortWeatherDto.setName(weatherDto.getName());
            shortWeatherDto.setTemperature(weatherDto.getTemperature());
            shorties.add(shortWeatherDto);
        }
        return shorties;
    }
    // END
}
