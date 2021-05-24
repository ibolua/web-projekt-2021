package de.hsrm.mi.web.projekt.foto;

import java.time.LocalDateTime;
import java.util.function.IntPredicate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Kommentar {

    @Id @GeneratedValue
    private long id;

    @Version
    private long version;

    private String autor = "";
    private LocalDateTime zeitpunkt = LocalDateTime.now();
    private String text = "";

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public LocalDateTime getZeitpunkt() {
        return zeitpunkt;
    }
    public void setZeitpunkt(LocalDateTime zeitpunkt) {
        this.zeitpunkt = zeitpunkt;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Kommentar other = (Kommentar) obj;
        if (id != other.id)
            return false;
        return true;
    }

    
    
}
