package reaktor.weatherapp.dao;

import org.springframework.data.repository.CrudRepository;
import reaktor.weatherapp.model.Perception;

import javax.transaction.Transactional;

@Transactional
public interface PerceptionDAO extends CrudRepository<Perception, Long>{

    public Perception findByPerceptionId(Long perceptionId);

}
