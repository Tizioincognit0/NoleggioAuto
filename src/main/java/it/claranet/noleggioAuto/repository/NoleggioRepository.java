package it.claranet.noleggioAuto.repository;

import it.claranet.noleggioAuto.model.Auto;
import it.claranet.noleggioAuto.model.Noleggio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoleggioRepository extends JpaRepository<Noleggio, Long> {

    public List<Noleggio> findByAuto(Optional<Auto> auto);
}
