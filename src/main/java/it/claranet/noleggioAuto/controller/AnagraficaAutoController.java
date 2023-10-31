package it.claranet.noleggioAuto.controller;

import it.claranet.noleggioAuto.dto.AutoDto;
import it.claranet.noleggioAuto.dto.AutoRequestBodyDto;
import it.claranet.noleggioAuto.service.AutoService;
import java.util.List;
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
public class AnagraficaAutoController {

    @Autowired
    AutoService srvAuto;

    @GetMapping("auto")
    public List<AutoDto> getListaAuto() {
        return srvAuto.lista();
    }

    @GetMapping("auto/filtro-marca/{idMarca}")
    public ResponseEntity<?> getListaAutoFiltrataPerMarche(
            @PathVariable Long idMarca
    ) {
        return srvAuto.lista(idMarca);
    }

    @PutMapping("auto/aggiungi")
    public ResponseEntity<String> aggiungiAuto(
            @RequestBody AutoRequestBodyDto autoDto
    ) {
        return srvAuto.aggiungi(autoDto);
    }

    @PostMapping("auto/modifica/{id}")
    public ResponseEntity<String> modificaAuto(
            @RequestBody AutoRequestBodyDto autoDto,
            @PathVariable Long id
    ) {
        return srvAuto.modifica(autoDto, id);
    }

    @DeleteMapping("auto/elimina/{id}")
    public ResponseEntity<String> eliminaAuto(
            @PathVariable Long id
    ) {
        return srvAuto.elimina(id);
    }

}
