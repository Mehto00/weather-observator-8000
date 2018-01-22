package reaktor.weatherapp.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reaktor.weatherapp.model.Perception;

@Repository
public interface PerceptionRepository extends CrudRepository<Perception, Long>{

    <S extends Perception> S save(S s);

}
