package entities.json;

import entities.db.StatusComanda;

public class RezultatComanda {

    private int id_comanda;
    private StatusComanda status_comanda;

    public RezultatComanda(int id_comanda, StatusComanda status_comanda) {
        this.id_comanda = id_comanda;
        this.status_comanda = status_comanda;
    }

    public int getId_comanda() {
        return id_comanda;
    }

    public void setId_comanda(int id_comanda) {
        this.id_comanda = id_comanda;
    }

    public StatusComanda getStatus_comanda() {
        return status_comanda;
    }

    public void setStatus_comanda(StatusComanda status_comanda) {
        this.status_comanda = status_comanda;
    }
}
