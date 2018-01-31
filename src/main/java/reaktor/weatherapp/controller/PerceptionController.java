package reaktor.weatherapp.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reaktor.weatherapp.dao.PerceptionDAO;
import reaktor.weatherapp.model.Perception;
import reaktor.weatherapp.model.Station;

import java.util.Date;

@CrossOrigin(maxAge = 3600)
@Controller
public class PerceptionController {

    @Autowired
    private PerceptionDAO perceptionDAO;

    // Creates a new Perception with current timestamp, temperature and pre-defined enum value of Station.
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

    // Lists all the perceptions in database as JSON
    @RequestMapping(value = "/all/perceptions", method = RequestMethod.GET)
    @ResponseBody
    public String displayAllPerceptions() {
        String response = "";
        Iterable<Perception> perception = perceptionDAO.findAll();
        response = response + perception.toString();
        return response;
    }

    // Lists all the perceptions by given station name in the URL as JSON
    @RequestMapping(value = "/all/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayPerceptionsFromStation(@PathVariable String stationName) {
        String response = "";
        Iterable<Perception> perceptionByStation = perceptionDAO.findAllByStationIs(Station.valueOf(stationName));
        response = response + perceptionByStation.toString();
        return response;
    }

    // Lists the latest
    @RequestMapping(value = "/latest/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayMinAndMaxByStation(@PathVariable String stationName) {
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);

        // This section is for printing out the latest submitted perception for the given station
        String perceptionId;
        String perceptionTemp;
        String perceptionTimestamp;
        String response = "";

        Iterable<Perception> perceptionIdByStation = perceptionDAO.findLatestPerceptionIdByStation(Station.valueOf(stationName));
        Iterable<Perception> perceptionTempByStation = perceptionDAO.findLatestTempByStation(Station.valueOf(stationName));
        Iterable<Perception> perceptionTimestampByStation = perceptionDAO.findLatestTimestampByStation(Station.valueOf(stationName));

        //Formatting out some unnecessary brackets from sql query
        perceptionId = perceptionIdByStation.toString();
        perceptionId = perceptionId.substring(1, perceptionId.length() -1);
        perceptionTemp = perceptionTempByStation.toString();
        perceptionTemp = perceptionTemp.substring(1, perceptionTemp.length() -1);
        perceptionTimestamp = perceptionTimestampByStation.toString();
        perceptionTimestamp = perceptionTimestamp.substring(1, perceptionTimestamp.length() -1);

        response = response + "[{\"perceptionId\" : \"" + perceptionId + "\",\"temperature\" : \"" + perceptionTemp + "\",\"timestamp\" : \"" + perceptionTimestamp + "\"},";

        // And this section is for printing out the max/min temperatures for the given station for the last 24 hours
        String perceptionMin;
        String perceptionMax;

        Iterable<Perception> stationMin = perceptionDAO.findMinTempByStation(Station.valueOf(stationName), yesterday);
        Iterable<Perception> stationMax = perceptionDAO.findMaxTempByStation(Station.valueOf(stationName), yesterday);

        //Formatting out some unnecessary brackets from sql query
        perceptionMin = stationMin.toString();
        perceptionMin = perceptionMin.substring(1, perceptionMin.length() -1);
        perceptionMax = stationMax.toString();
        perceptionMax = perceptionMax.substring(1, perceptionMax.length() -1);

        response = response + "{\"min\" : \"" + perceptionMin + "\", \"max\" : \"" + perceptionMax + "\"}]";
        return response;
    }

}

