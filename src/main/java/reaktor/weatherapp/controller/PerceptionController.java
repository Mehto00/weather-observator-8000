package reaktor.weatherapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reaktor.weatherapp.dao.PerceptionDAO;
import reaktor.weatherapp.model.Perception;
import reaktor.weatherapp.model.Station;

import java.util.Date;

@Controller
public class PerceptionController {

    @Autowired
    private PerceptionDAO perceptionDAO;

    @RequestMapping(value = "/perceptions", method = RequestMethod.POST)
    @ResponseBody
    public String create(String temperature, Station station){
        Long perceptionId;
        String timeStamp = new Date().toString();
        try {
            Perception perception = new Perception(timeStamp, temperature, station);
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


}

