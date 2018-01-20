package reaktor.weatherapp.repo;

import org.springframework.data.repository.CrudRepository;
import reaktor.weatherapp.model.Station;

public interface StationRepository extends CrudRepository<Station, Long> {
    @Override
    <S extends Station> Iterable<S> save(Iterable<S> iterable);

    @Override
    Iterable<Station> findAll();

    @Override
    void delete(Iterable<? extends Station> iterable);

    @Override
    void deleteAll();
}
