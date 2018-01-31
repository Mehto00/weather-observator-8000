package reaktor.weatherapp.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.DateUtils;
import reaktor.weatherapp.dao.PerceptionDAO;
import reaktor.weatherapp.model.Perception;
import reaktor.weatherapp.model.Station;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@Controller
public class PerceptionController {

    @Autowired
    private PerceptionDAO perceptionDAO;

    @RequestMapping(value = "/perceptions", method = RequestMethod.POST)
    @ResponseBody
    public String create(double temperature, Station station){
        Date timeStamp = new Date();
        try {
            Perception perception = new Perception(timeStamp, temperature, station);
            perceptionDAO.save(perception);
        }
        catch (Exception ex) {
            return "Error creating the perception: " + ex.toString();
        }
        return "Hooray! Your perception was successfully posted";
    }

    @RequestMapping(value = "/perceptions", method = RequestMethod.GET)
    @ResponseBody
    public String displayAllPerceptions() {
        String response = "";
        Iterable<Perception> perception = perceptionDAO.findAll();
        response = response + perception.toString();
        return response;
    }

    @RequestMapping(value = "/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayPerceptionsFromStation(@PathVariable String stationName) {
        String response = "";
        Iterable<Perception> perceptionByStation = perceptionDAO.findAllByStationIs(Station.valueOf(stationName));
        response = response + perceptionByStation.toString();
        return response;
    }

    @RequestMapping(value = "/test/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayLatestTemp(@PathVariable String stationName) {
        String response = "";
        Iterable<Perception> perceptionByStation = perceptionDAO.findLatestTempByStation(Station.valueOf(stationName));
        response = response + perceptionByStation.toString();
        return response;
    }

    @RequestMapping(value = "/min/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayMinAndMaxByStation(@PathVariable String stationName) {
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        String response = "";
        Iterable<Perception> stationMin = perceptionDAO.findMinTempByStation(Station.valueOf(stationName), yesterday);
        Iterable<Perception> stationMax = perceptionDAO.findMaxTempByStation(Station.valueOf(stationName), yesterday);
        response = response + "min : " + stationMin.toString() + " / max : " + stationMax.toString();
        return response;
    }




}

