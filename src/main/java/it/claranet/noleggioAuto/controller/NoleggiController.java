package it.claranet.noleggioAuto.controller;

import it.claranet.noleggioAuto.dto.NoleggioDto;
import it.claranet.noleggioAuto.dto.NoleggioRequestBodyDto;
import it.claranet.noleggioAuto.service.NoleggioService;
import java.util.List;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoleggiController {

    @Autowired
    NoleggioService srvNoleggio;

    @GetMapping("noleggi")
    public List<NoleggioDto> getListaNoleggi() {
        return srvNoleggio.lista();
    }

    @GetMapping("noleggi/filtro-auto/{idAuto}")
    public ResponseEntity<?> getListaNoleggiFiltrataPerAuto(
            @PathVariable Long idAuto
    ) throws NotFoundException {
        return srvNoleggio.lista(idAuto);
    }

    @PutMapping("noleggi/aggiungi")
    public ResponseEntity<String> aggiungiNoleggio(
            @RequestBody NoleggioRequestBodyDto noleggioDto
    ) {
        return srvNoleggio.aggiungi(noleggioDto);
    }

    @PostMapping("noleggi/modifica/{id}")
    public ResponseEntity<String> modificaNoleggio(
            @RequestBody NoleggioRequestBodyDto noleggioDto,
            @PathVariable Long id
    ) {
        return srvNoleggio.modifica(noleggioDto, id);
    }

    @DeleteMapping("noleggi/elimina/{id}")
    public ResponseEntity<String> eliminaNoleggio(
            @PathVariable Long id
    ) {
        return srvNoleggio.elimina(id);
    }
}
