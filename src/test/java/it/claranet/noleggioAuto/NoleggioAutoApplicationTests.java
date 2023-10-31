package it.claranet.noleggioAuto;

import it.claranet.noleggioAuto.model.Auto;
import it.claranet.noleggioAuto.model.Marca;
import it.claranet.noleggioAuto.repository.AutoRepository;
import it.claranet.noleggioAuto.repository.MarcaRepository;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoleggioAutoApplicationTests {

    @Autowired
    MarcaRepository repMarche;

    @Autowired
    AutoRepository repAuto;

    @Test
    public void contextLoads() {
        System.out.println("Inizio esecuzione test");
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testaInserimentoMarca() {

        Marca marca = new Marca("FIAT");
        marca = repMarche.save(marca);
        System.out.println("Dopo il salvataggio: " + marca.toString());

        Marca marcaRecuperata = repMarche.getOne(marca.getId());
        System.out.println("Dopo il recupero: " + marcaRecuperata.toString());

        assertNotNull(marca, "La marca recuperata è NULL");
        assertTrue(marca.equals(marcaRecuperata));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testaInserimentoAuto() {
        Date data = Date.valueOf("2021-05-09");
        
        Marca marca = new Marca("FIAT");
        marca = repMarche.save(marca);
        
        Auto auto = new Auto(marca, "GX T", 25.38, data, "QWERTY123");
        auto = repAuto.save(auto);
        System.out.println("Dopo il salvataggio: " + auto.toString());

        Auto autoRecuperata = repAuto.getOne(auto.getId());
        System.out.println("Dopo il recupero: " + autoRecuperata.toString());

        assertNotNull(auto, "L'auto recuperata è NULL");
        assertTrue(auto.equals(autoRecuperata));
    }
    

}
