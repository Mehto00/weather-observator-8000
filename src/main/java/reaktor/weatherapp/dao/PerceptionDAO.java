package reaktor.weatherapp.dao;

import org.springframework.data.repository.CrudRepository;
import reaktor.weatherapp.model.Perception;
import reaktor.weatherapp.model.Station;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PerceptionDAO extends CrudRepository<Perception,Long>{

    public List<Perception> findAll();

    public List<Perception> findAllByStationIs(Station station);

}
