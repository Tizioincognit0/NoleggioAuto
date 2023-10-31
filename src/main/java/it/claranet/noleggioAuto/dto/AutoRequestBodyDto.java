package it.claranet.noleggioAuto.dto;

import java.sql.Date;

public class AutoRequestBodyDto {

    private Long idMarca;

    private String modello;

    private Double cilindrata;

    private Date dataImmatricolazione;

    private String targa;

    public AutoRequestBodyDto(Long idMarca, String modello, Double cilindrata, Date dataImmatricolazione, String targa) {
        this.idMarca = idMarca;
        this.modello = modello;
        this.cilindrata = cilindrata;
        this.dataImmatricolazione = dataImmatricolazione;
        this.targa = targa;
    }

    public Date getDataImmatricolazione() {
        return dataImmatricolazione;
    }

    public void setDataImmatricolazione(Date dataImmatricolazione) {
        this.dataImmatricolazione = dataImmatricolazione;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public Double getCilindrata() {
        return cilindrata;
    }

    public void setCilindrata(Double cilindrata) {
        this.cilindrata = cilindrata;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

}
