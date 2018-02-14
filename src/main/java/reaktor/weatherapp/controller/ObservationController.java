package reaktor.weatherapp.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reaktor.weatherapp.dao.ObservationDAO;
import reaktor.weatherapp.model.Observation;
import reaktor.weatherapp.model.Station;

import java.util.Date;

@CrossOrigin(origins = "http://localhost:80", maxAge = 3600)
@Controller
public class ObservationController {

    @Autowired
    private ObservationDAO observationDAO;

    // Creates a new Observation with current timestamp, temperature and pre-defined enum value of Station.
    @RequestMapping(value = "/post-observation", method = RequestMethod.POST)
    @ResponseBody
    public String create(double temperature, Station station){
        Date timeStamp = new Date();
        try {
            Observation observation = new Observation(timeStamp, temperature, station);
            observation.setFormatedDate(observation.getTimestamp());
            observationDAO.save(observation);
        }
        catch (Exception ex) {
            return "Error creating the observation: " + ex.toString();
        }
        return "Hooray! Your observation was successfully posted";
    }

    // Lists all the observations in database as JSON
    @RequestMapping(value = "/all/observations", method = RequestMethod.GET)
    @ResponseBody
    public String displayAllObservation() {
        String response = "";
        Iterable<Observation> observation = observationDAO.findAll();
        response = response + observation.toString();
        return response;
    }

    // Lists all the observations by given station name in the URL as JSON
    @RequestMapping(value = "/all/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayObservationsFromStation(@PathVariable String stationName) {
        String response = "";
        Iterable<Observation> observationByStation = observationDAO.findAllByStationIs(Station.valueOf(stationName));
        response = response + observationByStation.toString();
        return response;
    }

    // Lists the latest
    @RequestMapping(value = "/latest/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayMinAndMaxByStation(@PathVariable String stationName) {
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);

        // This section is for printing out the latest submitted observation for the given station
        String observationId;
        String observationTemp;
        String observationTimestamp;
        String response = "";

        Iterable<Observation> observationIdByStation = observationDAO.findLatestObservationIdByStation(Station.valueOf(stationName));
        Iterable<Observation> observationTempByStation = observationDAO.findLatestTempByStation(Station.valueOf(stationName));
        Iterable<Observation> observationTimestampByStation = observationDAO.findLatestTimestampByStation(Station.valueOf(stationName));

        //Formatting out some unnecessary brackets from sql query
        observationId = observationIdByStation.toString();
        observationId = observationId.substring(1, observationId.length() -1);
        observationTemp = observationTempByStation.toString();
        observationTemp = observationTemp.substring(1, observationTemp.length() -1);
        observationTimestamp = observationTimestampByStation.toString();
        observationTimestamp = observationTimestamp.substring(1, observationTimestamp.length() -1);

        response = response + "[{\"observationId\" : \"" + observationId + "\",\"temperature\" : \"" + observationTemp + "\",\"timestamp\" : \"" + observationTimestamp + "\"},";

        // And this section is for printing out the max/min temperatures for the given station for the last 24 hours
        String observationMin;
        String observationMax;

        Iterable<Observation> stationMin = observationDAO.findMinTempByStation(Station.valueOf(stationName), yesterday);
        Iterable<Observation> stationMax = observationDAO.findMaxTempByStation(Station.valueOf(stationName), yesterday);

        //Formatting out some unnecessary brackets from sql query
        observationMin = stationMin.toString();
        observationMin = observationMin.substring(1, observationMin.length() -1);
        observationMax = stationMax.toString();
        observationMax = observationMax.substring(1, observationMax.length() -1);

        response = response + "{\"min\" : \"" + observationMin + "\", \"max\" : \"" + observationMax + "\"}]";
        return response;
    }

}

