package de.hsrm.mi.web.projekt.spruch;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;

@Entity
public class Spruch {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    @NotBlank
    private String name = "";
    @NotBlank
    private String text = "";
    private int anspruch = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAnspruch() {
        return anspruch;
    }

    public void setAnspruch(int anspruch) {
        this.anspruch = anspruch;
    }

}