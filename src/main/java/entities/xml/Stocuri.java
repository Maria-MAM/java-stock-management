package entities.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "Stocuri")
@XmlAccessorType(XmlAccessType.FIELD)
public class Stocuri implements Serializable {

    @XmlElement(name = "Stoc")
    private List<Stoc> stoc;

    public Stocuri() {
    }

    public Stocuri(List<Stoc> stoc) {
        this.stoc = stoc;
    }

    public List<Stoc> getStoc() {
        return stoc;
    }

    public void setStoc(List<Stoc> stoc) {
        this.stoc = stoc;
    }
}
