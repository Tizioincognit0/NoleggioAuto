package it.claranet.noleggioAuto.repository;

import it.claranet.noleggioAuto.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    
    public Marca findByBrand(String brand);
}
