package it.claranet.noleggioAuto.service;

import it.claranet.noleggioAuto.dto.NoleggioDto;
import it.claranet.noleggioAuto.dto.NoleggioRequestBodyDto;
import it.claranet.noleggioAuto.model.Auto;
import it.claranet.noleggioAuto.model.Noleggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import it.claranet.noleggioAuto.repository.AutoRepository;
import it.claranet.noleggioAuto.repository.NoleggioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class NoleggioService {

    @Autowired
    NoleggioRepository repNoleggio;

    @Autowired
    AutoRepository repAuto;

    @Autowired
    private ModelMapper modelMapper;

    public List<NoleggioDto> lista() {
        List<Noleggio> lista = repNoleggio.findAll();
        return lista.stream()
                .map(this::convertiInDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> lista(Long idAuto) {
        Optional<Auto> auto = repAuto.findById(idAuto);

        if (!auto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna auto trovata con id " + idAuto);
        }

        List<Noleggio> lista = repNoleggio.findByAuto(auto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(lista.stream()
                .map(this::convertiInDto)
                .collect(Collectors.toList()));
    }

    public ResponseEntity<String> aggiungi(NoleggioRequestBodyDto noleggioDto) {
        ResponseEntity<String> errore = validaDto(noleggioDto);

        if (errore != null) {
            return errore;
        }

        Optional<Auto> auto = repAuto.findById(noleggioDto.getIdAuto());

        if (!auto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna auto trovata con id " + noleggioDto.getIdAuto());
        }

        if (auto.get().hasNoleggio(noleggioDto.getDataInizio(), noleggioDto.getDataFine())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("L'auto con id " + noleggioDto.getIdAuto() + " è già stata porenotata per un noleggio nel periodo "
                            + "compreso tra il " + noleggioDto.getDataInizio() + " e il " + noleggioDto.getDataFine());
        }

        repNoleggio.save(new Noleggio(noleggioDto.getDataInizio(), noleggioDto.getDataFine(),
                noleggioDto.getPrezzo(), auto.get()));

        return ResponseEntity.status(HttpStatus.OK)
                .body("Noleggio aggiunto con successo");

    }

    public ResponseEntity<String> modifica(NoleggioRequestBodyDto noleggioDto, Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo id è obbligatorio");
        }

        ResponseEntity<String> errore = validaDto(noleggioDto);

        if (errore != null) {
            return errore;
        }

        if (!repNoleggio.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessun noleggio trovato con id " + id);
        }

        if (noleggioDto.getDataInizio().after(noleggioDto.getDataFine())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("La data inizio dev'essere antecedente alla data fine");
        }

        Optional<Auto> auto = repAuto.findById(noleggioDto.getIdAuto());

        if (!auto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessuna auto trovata con id " + noleggioDto.getIdAuto());
        }

        if (auto.get().hasNoleggio(noleggioDto.getDataInizio(), noleggioDto.getDataFine())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("L'auto con id " + noleggioDto.getIdAuto() + " è già stata porenotata per un noleggio nel periodo "
                            + "compreso tra il " + noleggioDto.getDataInizio() + " e il " + noleggioDto.getDataFine());
        }

        repNoleggio.save(new Noleggio(id, noleggioDto.getDataInizio(), noleggioDto.getDataFine(),
                noleggioDto.getPrezzo(), auto.get()));

        return ResponseEntity.status(HttpStatus.OK)
                .body("Noleggio modificato con successo");
    }

    public ResponseEntity<String> elimina(Long id) {
        if (!repNoleggio.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nessun noleggio trovato con id " + id);
        }

        repNoleggio.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Noleggio eliminato con successo");
    }

    private NoleggioDto convertiInDto(Noleggio noleggio) {
        return modelMapper.map(noleggio, NoleggioDto.class);
    }

    private ResponseEntity<String> validaDto(NoleggioRequestBodyDto noleggioDto) {
        if (noleggioDto.getDataInizio() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo dataInizio è obbligatorio");
        }

        if (noleggioDto.getDataFine() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo dataFine è obbligatorio");
        }
        if (noleggioDto.getPrezzo() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo prezzo è obbligatorio");
        }

        if (noleggioDto.getIdAuto() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Il campo idAuto è obbligatorio");
        }

        if (noleggioDto.getDataInizio().after(noleggioDto.getDataFine())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("La data inizio dev'essere antecedente alla data fine");
        }

        return null;
    }
}
