package entities.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Stoc")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Stoc implements Serializable {

    private Integer id_produs;
    private Integer stoc;

    public Stoc() {
    }

    public Stoc(Integer id_produs, Integer stoc) {
        this.id_produs = id_produs;
        this.stoc = stoc;
    }

    public Integer getId_produs() {
        return id_produs;
    }

    public void setId_produs(Integer id_produs) {
        this.id_produs = id_produs;
    }

    public Integer getStoc() {
        return stoc;
    }

    public void setStoc(Integer stoc) {
        this.stoc = stoc;
    }
}
