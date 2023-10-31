package it.claranet.noleggioAuto.dto;

import java.sql.Date;

public class NoleggioRequestBodyDto {

    private Date dataInizio;

    private Date dataFine;

    private Double prezzo;

    private Long idAuto;

    public NoleggioRequestBodyDto(Date dataInizio, Date dataFine, Double prezzo, Long idAuto) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.prezzo = prezzo;
        this.idAuto = idAuto;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Long getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(Long idAuto) {
        this.idAuto = idAuto;
    }

}
