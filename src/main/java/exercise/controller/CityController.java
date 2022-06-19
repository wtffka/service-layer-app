package exercise.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.CityNotFoundException;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.ShortWeatherDto;
import exercise.service.WeatherDto;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    // BEGIN
    @GetMapping(path = "/cities/{id}")
    public WeatherDto getCityById(@PathVariable Long id) throws JsonProcessingException {
        if (cityRepository.findById(id) != null) {
            String city = cityRepository.findById(id).get().getName();
            return weatherService.getWeatherForOneCity(city);
        }
        throw new CityNotFoundException("City with that id not found");
    }

    @GetMapping(path = "/cities")
    public Iterable<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping(path = "/search")
    public List<ShortWeatherDto> getCitiesByPrefixName(
            @RequestParam(value = "name", required = false) String name) throws JsonProcessingException {
        List<String> cities = new ArrayList<>();
        if (name != null) {
            cityRepository.findNameByNameStartingWithIgnoreCase(name).forEach(x -> cities.add(x.getName()));
            return weatherService.getWeatherForCities(cities);
        }
        cityRepository.findAllByOrderByName().forEach(x -> cities.add(x.getName()));
        return weatherService.getWeatherForCities(cities);
    }
    // END
}

