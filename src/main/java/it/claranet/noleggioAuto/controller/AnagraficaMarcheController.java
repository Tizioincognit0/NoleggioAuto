package it.claranet.noleggioAuto.controller;

import it.claranet.noleggioAuto.dto.MarcaDto;
import it.claranet.noleggioAuto.dto.MarcaRequestBodyDto;
import it.claranet.noleggioAuto.service.MarcaService;
import java.util.List;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnagraficaMarcheController {

    @Autowired
    private MarcaService srvMarca;

    @GetMapping("marche")
    public List<MarcaDto> getListaMarche() {
        return srvMarca.lista();
    }

    @PutMapping("marche/aggiungi")
    public ResponseEntity<String> aggiungiMarca(
            ModelMap model,
            @RequestBody MarcaRequestBodyDto marcaDto
    ) {
        return srvMarca.aggiungi(marcaDto);
    }

    @PostMapping("marche/modifica")
    public ResponseEntity<String> modificaMarca(
            @RequestBody MarcaDto marcaDto
    ) {
        return srvMarca.modifica(marcaDto);
    }

    @DeleteMapping("marche/elimina/{id}")
    public ResponseEntity<String> eliminaMarca(
            @PathVariable Long id
    ) throws NotFoundException {
        return srvMarca.elimina(id);
    }

}
