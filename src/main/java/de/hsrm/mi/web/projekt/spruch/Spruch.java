package de.hsrm.mi.web.projekt.spruch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private int gewicht = 0;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

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

    public int getGewicht() {
        return gewicht;
    }

    public void setGewicht(int gewicht) {
        this.gewicht = gewicht;
    }

}
