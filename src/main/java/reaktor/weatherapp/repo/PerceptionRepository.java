package reaktor.weatherapp.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import reaktor.weatherapp.model.Perception;

@Repository
public interface PerceptionRepository extends CrudRepository<Perception, Long>{

    @Override
    <S extends Perception> Iterable<S> save(Iterable<S> iterable);

    @Override
    Iterable<Perception> findAll();

    @Override
    void delete(Iterable<? extends Perception> iterable);

    @Override
    void deleteAll();
}
