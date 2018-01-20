package reaktor.weatherapp.dao;

import org.springframework.data.repository.CrudRepository;
import reaktor.weatherapp.model.Station;

import javax.transaction.Transactional;

@Transactional
public interface StationDAO extends CrudRepository<Station, Long> {
}
