package it.claranet.noleggioAuto.repository;

import it.claranet.noleggioAuto.model.Auto;
import it.claranet.noleggioAuto.model.Marca;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
    public List<Auto> findByMarca(Optional<Marca> marca);

}
