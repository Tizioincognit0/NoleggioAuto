package it.claranet.noleggioAuto.service;

import com.google.common.base.Strings;
import it.claranet.noleggioAuto.dto.MarcaDto;
import it.claranet.noleggioAuto.dto.MarcaRequestBodyDto;
import it.claranet.noleggioAuto.model.Marca;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import it.claranet.noleggioAuto.repository.AutoRepository;
import it.claranet.noleggioAuto.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository repMarca;

    @Autowired
    private AutoRepository repAuto;

    @Autowired
    private ModelMapper modelMapper;

    public List<MarcaDto> lista() {
        List<Marca> lista = repMarca.findAll();
        return lista.stream()
                .map(this::convertiInDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> aggiungi(MarcaRequestBodyDto marcaDto) {
        if (Strings.isNullOrEmpty(marcaDto.getBrand())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo brand è obbligatorio");
        }

        repMarca.save(new Marca(marcaDto.getBrand()));

        return ResponseEntity.status(HttpStatus.OK)
                .body("Marca aggiunta con successo");
    }

    public ResponseEntity<String> modifica(MarcaDto marcaDto) {
        if (marcaDto.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo id è obbligatorio");
        }

        if (Strings.isNullOrEmpty(marcaDto.getBrand())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo brand è obbligatorio");
        }

        if (!repMarca.findById(marcaDto.getId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna marca trovata con id " + marcaDto.getId());
        }

        repMarca.save(new Marca(marcaDto.getId(), marcaDto.getBrand()));

        return ResponseEntity.status(HttpStatus.OK)
                .body("Marca modificata con successo");
    }

    public ResponseEntity<String> elimina(Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo id è obbligatorio");
        }

        Optional<Marca> marca = repMarca.findById(id);

        if (!marca.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna marca trovata con id " + id);
        }

        if (!repAuto.findByMarca(marca).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La marca con id " + id + " non può essere eliminata in quanto associata ad almeno un'auto");
        }

        repMarca.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Marca eliminata con successo");
    }

    private MarcaDto convertiInDto(Marca marca) {
        return modelMapper.map(marca, MarcaDto.class);
    }

}
