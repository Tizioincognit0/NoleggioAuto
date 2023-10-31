package it.claranet.noleggioAuto.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Auto")
public class Auto implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Marca", referencedColumnName = "id", nullable = false)
    private Marca marca;

    @Column(name = "modello", nullable = false)
    private String modello;

    @Column(name = "cilindrata", nullable = false)
    private Double cilindrata;

    @Column(name = "data_immatricolazione", nullable = false)
    private Date dataImmatricolazione;

    @Column(name = "targa", nullable = false)
    private String targa;

    @OneToMany(mappedBy = "auto", fetch = FetchType.LAZY)
    private List<Noleggio> noleggi = new ArrayList<>();

    public Auto(Long id, Marca marca, String modello, Double cilindrata, Date data, String targa) {
        this.id = id;
        this.marca = marca;
        this.modello = modello;
        this.cilindrata = cilindrata;
        this.dataImmatricolazione = data;
        this.targa = targa;
    }

    public Auto(Marca marca, String modello, Double cilindrata, Date data, String targa) {
        this.marca = marca;
        this.modello = modello;
        this.cilindrata = cilindrata;
        this.dataImmatricolazione = data;
        this.targa = targa;
    }

    public Auto() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auto other = (Auto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Date getDataImmatricolazione() {
        return dataImmatricolazione;
    }

    public void setDataImmatricolazione(Date dataImmatricolazione) {
        this.dataImmatricolazione = dataImmatricolazione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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

    public List<Noleggio> getNoleggi() {
        return noleggi;
    }

    public void setNoleggi(List<Noleggio> noleggi) {
        this.noleggi = noleggi;
    }

    @Override
    public String toString() {
        return "Auto{" + "id=" + id + ", marca=" + marca + ", modello=" + modello + ", cilindrata=" + cilindrata + ", dataImmatricolazione=" + dataImmatricolazione + ", targa=" + targa + ", noleggi=" + noleggi + '}';
    }

    public boolean hasNoleggio(Date dataInizio, Date dataFine) {
        return this.getNoleggi().stream().anyMatch((Noleggio n)
                -> (n.getDataInizio().after(dataInizio)
                && n.getDataInizio().before(dataFine)
                || (n.getDataFine().after(dataInizio)
                && n.getDataFine().before(dataFine))));
    }

}
