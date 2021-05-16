package de.hsrm.mi.web.projekt.sichtung;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Sichtung {
    @NotEmpty(message = "Name darf nicht leer sein.")
    @Size(min = 3, message = "Name muss mindestens 3 Zeichen lang sein.")
    private String name;
    @NotEmpty(message = "Ort darf nicht leer sein.")
    private String ort;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Datum darf nicht null sein.")
    private LocalDate datum;
    @NotEmpty(message = "Beschreibung darf nicht leer sein.")
    @Size(max = 80, message = "Beschreibung darf maximal 80 Zeichen lang sein.")
    private String beschreibung;
    

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getOrt() {
        return this.ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getBeschreibung() {
        return this.beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", ort='" + getOrt() + "'" +
            ", datum='" + getDatum() + "'" +
            ", beschreibung='" + getBeschreibung() + "'" +
            "}";
    }

}
