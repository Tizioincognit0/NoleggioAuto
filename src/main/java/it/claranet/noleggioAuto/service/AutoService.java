package it.claranet.noleggioAuto.service;

import com.google.common.base.Strings;
import it.claranet.noleggioAuto.dto.AutoDto;
import it.claranet.noleggioAuto.dto.AutoRequestBodyDto;
import it.claranet.noleggioAuto.model.Auto;
import it.claranet.noleggioAuto.model.Marca;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import it.claranet.noleggioAuto.repository.AutoRepository;
import it.claranet.noleggioAuto.repository.MarcaRepository;

@Service
public class AutoService {

    @Autowired
    AutoRepository repAuto;

    @Autowired
    MarcaRepository repMarca;

    @Autowired
    private ModelMapper modelMapper;

    public List<AutoDto> lista() {
        List<Auto> lista = repAuto.findAll();
        return lista.stream()
                .map(this::convertiInDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?>  lista(Long idMarca) {
        Optional<Marca> marca = repMarca.findById(idMarca);

        if (!marca.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna marca trovata con id " + idMarca);
        }

        List<Auto> lista = repAuto.findByMarca(marca);
        return ResponseEntity.status(HttpStatus.OK)
                .body(lista.stream()
                .map(this::convertiInDto)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<String> aggiungi(AutoRequestBodyDto autoDto) {
        ResponseEntity<String> errore = validaDto(autoDto);

        if (errore != null) {
            return errore;
        }

        Optional<Marca> marca = repMarca.findById(autoDto.getIdMarca());

        if (!marca.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna marca trovata con id " + autoDto.getIdMarca());
        }

        repAuto.save(new Auto(marca.get(), autoDto.getModello(),
                autoDto.getCilindrata(), autoDto.getDataImmatricolazione(),
                autoDto.getTarga()));

        return ResponseEntity.status(HttpStatus.OK)
                .body("Auto aggiunta con successo");
    }

    public ResponseEntity<String> modifica(AutoRequestBodyDto autoDto, Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo id è obbligatorio");
        }

        ResponseEntity<String> errore = validaDto(autoDto);

        if (errore != null) {
            return errore;
        }

        if (!repAuto.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna auto trovata con id " + id);
        }

        Optional<Marca> marca = repMarca.findById(autoDto.getIdMarca());

        if (!marca.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna marca trovata con id " + autoDto.getIdMarca());
        }

        repAuto.save(new Auto(id, marca.get(), autoDto.getModello(),
                autoDto.getCilindrata(), autoDto.getDataImmatricolazione(),
                autoDto.getTarga()));

        return ResponseEntity.status(HttpStatus.OK)
                .body("Auto modificata con successo");
    }

    public ResponseEntity<String> elimina(Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo id è obbligatorio");
        }

        Optional<Auto> auto = repAuto.findById(id);

        if (!auto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna auto trovata con id " + id);
        }

        if (!auto.get().getNoleggi().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("L'auto con id " + id + " non può essere eliminata in quanto associata ad almeno un noleggio");
        }

        repAuto.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Auto eliminata con successo");
    }

    private AutoDto convertiInDto(Auto auto) {
        return modelMapper.map(auto, AutoDto.class);
    }

    private ResponseEntity<String> validaDto(AutoRequestBodyDto autoDto) {
        if (autoDto.getIdMarca() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo idMarca è obbligatorio");
        }

        if (Strings.isNullOrEmpty(autoDto.getModello())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo modello è obbligatorio");
        }

        if (autoDto.getCilindrata() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo cilindrata è obbligatorio");
        }

        if (autoDto.getDataImmatricolazione() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo dataImmatricolazione è obbligatorio");
        }

        if (Strings.isNullOrEmpty(autoDto.getTarga())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo targa è obbligatorio");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("Validazione passata");
    }

}
