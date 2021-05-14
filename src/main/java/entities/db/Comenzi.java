package entities.db;

import javax.persistence.*;

@Entity(name = "comenzi")
@Table(name = "comenzi")
public class Comenzi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComanda")
    private int idComanda;
    @ManyToOne(optional = false)
    @JoinColumn(name = "produse")
    private Produse produs;
    private String numeClient;
    @Enumerated(EnumType.STRING)
    private StatusComanda statusComanda;

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public Produse getProdus() {
        return produs;
    }

    public void setProdus(Produse produs) {
        this.produs = produs;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public StatusComanda getStatusComanda() {
        return statusComanda;
    }

    public void setStatusComanda(StatusComanda statusComanda) {
        this.statusComanda = statusComanda;
    }
}
