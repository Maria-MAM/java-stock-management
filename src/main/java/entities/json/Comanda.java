package entities.json;

public class Comanda {

    private String nume_client;
    private int id_produs;
    private int cantitate;

    public String getNume_client() {
        return nume_client;
    }

    public void setNume_client(String nume_client) {
        this.nume_client = nume_client;
    }

    public int getId_produs() {
        return id_produs;
    }

    public void setId_produs(int id_produs) {
        this.id_produs = id_produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "nume_client='" + nume_client + '\'' +
                ", id_produs='" + id_produs + '\'' +
                ", cantitate=" + cantitate +
                '}';
    }
}
