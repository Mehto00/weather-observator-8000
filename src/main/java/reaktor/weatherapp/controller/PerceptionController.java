package reaktor.weatherapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reaktor.weatherapp.dao.PerceptionDAO;
import reaktor.weatherapp.dao.StationDAO;
import reaktor.weatherapp.model.Perception;
import reaktor.weatherapp.model.Station;

@Controller
public class PerceptionController {

    @Autowired
    private PerceptionDAO perceptionDAO;

    @Autowired
    private StationDAO stationDAO;

    @RequestMapping(value = "/perceptions", method = RequestMethod.POST)
    @ResponseBody
    public String create(String stationName, String temperature){
        Long perceptionId;
        try {
            Perception perception = new Perception();
            perceptionDAO.save(perception);
            perceptionId = perception.getPerceptionId();
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created with id = " + perceptionId;
    }

    @RequestMapping(value = "/perceptions", method = RequestMethod.GET)
    @ResponseBody
    public String displayPerceptions() {
        Iterable<Perception> findAll = perceptionDAO.findAll();
        String displayPerceptions = findAll.toString();
        return displayPerceptions;
    }

    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    @ResponseBody
    public String displayStations() {
        Iterable<Station> findAll = stationDAO.findAll();
        String displayStations = findAll.toString();
        return displayStations;
    }

/*    @RequestMapping(value = "/perceptions", method = RequestMethod.PUT)
    @ResponseBody
    public String updatePerceptions(String stationName, String temperature) {
        try {
        Perception perception = perceptionDAO.findByStationName(stationName);
        perception.setTemperature(temperature);
        return "Temperature to " + stationName + "successfully updated";}
        catch (Exception e) {
            return e.toString();
        }
    }*/

}

