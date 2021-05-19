package de.hsrm.mi.web.projekt.foto;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Foto {
    
    // private static final double Null = 0;

    @Id @GeneratedValue
    private long id;

    @Version
    private long version;

    @NotBlank
    private String mimetype = "";
    @Size(min=3)
    private String dateiname = "";
    private String ort = "";
    @Future
    private LocalDateTime zeitstempel = LocalDateTime.MIN;

    private double geolaenge = 0f;
    private double geobreite = 0f;

    @Lob
    private byte[] fotodaten;

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getDateiname() {
        return dateiname;
    }

    public void setDateiname(String dateiname) {
        this.dateiname = dateiname;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public LocalDateTime getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(LocalDateTime zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    public double getGeolaenge() {
        return geolaenge;
    }

    public void setGeolaenge(double geolaenge) {
        this.geolaenge = geolaenge;
    }

    public double getGeobreite() {
        return geobreite;
    }

    public void setGeobreite(double geobreite) {
        this.geobreite = geobreite;
    }

    public byte[] getFotodaten() {
        return fotodaten;
    }

    public void setFotodaten(byte[] fotodaten) {
        this.fotodaten = fotodaten;
    }

    @Override
    public String toString() {
        return "Foto [dateiname=" + dateiname + ", fotodaten=" + Arrays.toString(fotodaten) + ", geobreite=" + geobreite
                + ", geolaenge=" + geolaenge + ", mimetype=" + mimetype + ", ort=" + ort + ", zeitstempel="
                + zeitstempel + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateiname == null) ? 0 : dateiname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Foto other = (Foto) obj;
        if (dateiname == null) {
            if (other.dateiname != null)
                return false;
        } else if (!dateiname.equals(other.dateiname))
            return false;
        return true;
    }


    
}