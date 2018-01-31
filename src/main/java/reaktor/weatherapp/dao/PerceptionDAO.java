package reaktor.weatherapp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import reaktor.weatherapp.model.Perception;
import reaktor.weatherapp.model.Station;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface PerceptionDAO extends CrudRepository<Perception,Long>{

    public List<Perception> findAll();

    public List<Perception> findAllByStationIs(Station station);

    // Queries for printing out the properties of the last submitted perception by given station name
    @Query(value = "SELECT temperature FROM Perception p WHERE p.perceptionId = (SELECT MAX(perceptionId) FROM Perception p WHERE p.station = :station)")
    public List<Perception> findLatestTempByStation(@Param("station") Station station);

    @Query(value = "SELECT perceptionId FROM Perception p WHERE p.perceptionId = (SELECT MAX(perceptionId) FROM Perception p WHERE p.station = :station)")
    public List<Perception> findLatestPerceptionIdByStation(@Param("station") Station station);

    @Query(value = "SELECT timestamp FROM Perception p WHERE p.perceptionId = (SELECT MAX(perceptionId) FROM Perception p WHERE p.station = :station)")
    public List<Perception> findLatestTimestampByStation(@Param("station") Station station);


    // Queries for printing out the min/max temperatures for the last 24 hours by given station name
    @Query(value = "SELECT MIN(temperature) FROM Perception p WHERE p.station = :station AND p.timestamp >= :yesterday")
    public List<Perception> findMinTempByStation(@Param("station") Station station, @Param("yesterday") Date yesterday);

    @Query(value = "SELECT MAX(temperature) FROM Perception p WHERE p.station = :station AND p.timestamp >= :yesterday")
    public List<Perception> findMaxTempByStation(@Param("station") Station station, @Param("yesterday") Date yesterday);

}
