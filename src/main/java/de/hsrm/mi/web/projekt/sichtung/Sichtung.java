package de.hsrm.mi.web.projekt.sichtung;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import de.hsrm.mi.web.projekt.validierung.Siebzehnhaft;

public class Sichtung {

    @NotBlank(message = "{sichtung.name.notblank}")
    @Size(min = 3, message = "{sichtung.name.min}")
    private String name;

    @NotBlank(message = "{sichtung.ort.notblank}")
    private String ort;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "{sichtung.datum.notnull}")
    private LocalDate datum;

    @NotBlank(message = "{sichtung.beschreibung.notblank}")
    @Size(max = 80, message = "{sichtung.beschreibung.size}")
    @Siebzehnhaft(message = "{sichtung.beschreibung.siebzehn}")
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
