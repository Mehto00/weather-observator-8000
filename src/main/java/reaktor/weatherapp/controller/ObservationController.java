package reaktor.weatherapp.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import reaktor.weatherapp.dao.ObservationDAO;
import reaktor.weatherapp.model.Observation;
import reaktor.weatherapp.model.Station;

import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(origins = "http:// - ENABLE-CORS-FROM-URL - ")
@RestController
public class ObservationController {

    @Autowired
    private ObservationDAO observationDAO;

    // Creates a new Observation with current timestamp, temperature and pre-defined enum value of Station.
    @RequestMapping(value = "/observations", method = RequestMethod.POST)
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

    // Lists all the observations from database as JSON
    @RequestMapping(value = "/observations", method = RequestMethod.GET)
    @ResponseBody
    public String displayAllObservation() {
        Iterable<Observation> observation = observationDAO.findAll();
        observation.forEach(observation1 -> observation1.setFormatedDate(observation1.getTimestamp()));
        String displayObservation = observation.toString();
        return displayObservation;
    }

    // Lists all the observations of one station by given URL as JSON
    @RequestMapping(value = "/observations/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayObservationsFromStation(@PathVariable String stationName) {
        Iterable<Observation> observationByStation = observationDAO.findAllByStationIs(Station.valueOf(stationName));
        observationByStation.forEach(observation1 -> observation1.setFormatedDate(observation1.getTimestamp()));
        String displayObservation = observationByStation.toString();
        return displayObservation;
    }

    // Lists the all latest observations by station
    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    @ResponseBody
    public String displayAllMinAndMaxFromStations() {

        String response = "";

        // Loops through Station enum and adds data to response String
        for (Station station : Station.values()) {

            // Creates a reference for querying observations from the last 24 hours
            Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);

            //Pattern and SimpleDateFormat for formating
            String pattern = "dd/MM/yyyy HH:mm";
            SimpleDateFormat df = new SimpleDateFormat(pattern);

            // Fetch latest submitted observation from db and max and min values of the referenced station from the last 24 huors
            Iterable<Observation> observationIdByStation = observationDAO.findLatestObservationIdByStation(station);
            Iterable<Observation> observationTempByStation = observationDAO.findLatestTempByStation(station);
            Date observationTimestampByStation = observationDAO.findLatestTimestampByStation(station);
            Iterable<Observation> stationMax = observationDAO.findMaxTempByStation(station, yesterday);
            Iterable<Observation> stationMin = observationDAO.findMinTempByStation(station, yesterday);

            // Format fetched data into a String and cut out unnecessary brackets from the beginning and end
            String observationId = observationIdByStation.toString().substring(1, observationIdByStation.toString().length() -1);
            String observationTemp = observationTempByStation.toString().substring(1, observationTempByStation.toString().length() -1);
            String observationMin = stationMin.toString().substring(1, stationMin.toString().length() -1);
            String observationMax = stationMax.toString().substring(1, stationMax.toString().length() -1);

            // Check if there's a observationTimestampByStation that can be formatted
            String observationTimestamp;
            try {
                observationTimestamp = df.format(observationTimestampByStation);
            } catch (Exception e) {observationTimestamp = "";}

            Observation observation = new Observation(observationTimestamp, Double.parseDouble(observationTemp), station, observationMax, observationMin);
            // setTheObservationId to point to the right observation
            observation.setObservationId(Long.parseLong(observationId));
            // format Date to correct "dd/MM/yyyy HH:mm" form
            observation.setFormatedDate(observationTimestampByStation);

            response = response + observation.printAll() + ",";
        }
            response = "[ " + response.substring(0 , response.toString().length() -1) + "]";

        return response;

    }

    // Lists the latest from one station by URL as JSON
    @RequestMapping(value = "/latest/{stationName}", method = RequestMethod.GET)
    @ResponseBody
    public String displayMinAndMaxByStation(@PathVariable String stationName) {

        String response = "";

        // Creates a reference for querying observations from the last 24 hours
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);

        //Pattern and SimpleDateFormat for formating
        String pattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat df = new SimpleDateFormat(pattern);

        // Fetch latest submitted observation from db and max and min values of the referenced station from the last 24 huors
        Iterable<Observation> observationIdByStation = observationDAO.findLatestObservationIdByStation(Station.valueOf(stationName));
        Iterable<Observation> observationTempByStation = observationDAO.findLatestTempByStation(Station.valueOf(stationName));
        Date observationTimestampByStation = observationDAO.findLatestTimestampByStation(Station.valueOf(stationName));
        Iterable<Observation> stationMax = observationDAO.findMaxTempByStation(Station.valueOf(stationName), yesterday);
        Iterable<Observation> stationMin = observationDAO.findMinTempByStation(Station.valueOf(stationName), yesterday);

        // Format fetched data into a String and cut out unnecessary brackets from the beginning and end
        String observationId = observationIdByStation.toString().substring(1, observationIdByStation.toString().length() -1);
        String observationTemp = observationTempByStation.toString().substring(1, observationTempByStation.toString().length() -1);
        String observationMin = stationMin.toString().substring(1, stationMin.toString().length() -1);
        String observationMax = stationMax.toString().substring(1, stationMax.toString().length() -1);

        // Check if there's a observationTimestampByStation that can be formatted
        String observationTimestamp;
        try {
            observationTimestamp = df.format(observationTimestampByStation);
        } catch (Exception e) {observationTimestamp = "";}

        Observation observation = new Observation(observationTimestamp, Double.parseDouble(observationTemp), Station.valueOf(stationName), observationMax, observationMin);
        // setTheObservationId to point to the right Observation
        observation.setObservationId(Long.parseLong(observationId));
        // format Date to correct "dd/MM/yyyy HH:mm" form
        observation.setFormatedDate(observationTimestampByStation);

        response = response + observation.printAll() + ",";

        response = "[ " + response.substring(0 , response.toString().length() -1) + "]";

        return response;
    }

}

