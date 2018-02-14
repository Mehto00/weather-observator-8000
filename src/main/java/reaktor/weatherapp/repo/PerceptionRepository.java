package reaktor.weatherapp.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reaktor.weatherapp.model.Observation;

@Repository
public interface PerceptionRepository extends CrudRepository<Observation, Long>{

    <S extends Observation> S save(S s);

}
