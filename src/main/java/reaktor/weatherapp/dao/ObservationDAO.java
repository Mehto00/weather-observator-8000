package reaktor.weatherapp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import reaktor.weatherapp.model.Observation;
import reaktor.weatherapp.model.Station;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface ObservationDAO extends CrudRepository<Observation,Long>{

    public List<Observation> findAll();

    public List<Observation> findAllByStationIs(Station station);

    // Queries for printing out the properties of the last submitted observation by given station name
    @Query(value = "SELECT temperature FROM Observation p WHERE p.observationId = (SELECT MAX(observationId) FROM Observation p WHERE p.station = :station)")
    public List<Observation> findLatestTempByStation(@Param("station") Station station);

    @Query(value = "SELECT observationId FROM Observation p WHERE p.observationId = (SELECT MAX(observationId) FROM Observation p WHERE p.station = :station)")
    public List<Observation> findLatestObservationIdByStation(@Param("station") Station station);

    @Query(value = "SELECT timestamp FROM Observation p WHERE p.observationId = (SELECT MAX(observationId) FROM Observation p WHERE p.station = :station)")
    public List<Observation> findLatestTimestampByStation(@Param("station") Station station);


    // Queries for printing out the min/max temperatures for the last 24 hours by given station name
    @Query(value = "SELECT MIN(temperature) FROM Observation p WHERE p.station = :station AND p.timestamp >= :yesterday")
    public List<Observation> findMinTempByStation(@Param("station") Station station, @Param("yesterday") Date yesterday);

    @Query(value = "SELECT MAX(temperature) FROM Observation p WHERE p.station = :station AND p.timestamp >= :yesterday")
    public List<Observation> findMaxTempByStation(@Param("station") Station station, @Param("yesterday") Date yesterday);

}
