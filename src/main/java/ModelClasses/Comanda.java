package ModelClasses;

public class Comanda {
   public int id;
   public String nume_client;
   public String nume_produs;
   public int cantitate;

    public Comanda(int id,String nume_client, String nume_produs,int cantitate) {
        this.id = id;
        this.nume_client = nume_client;
        this.nume_produs = nume_produs;
        this.cantitate = cantitate;
    }

    public Comanda() {
    }

    public int getId() {
        return id;
    }

    public String getNume_client() {
        return nume_client;
    }

    public String getNume_produs() {
        return nume_produs;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setNume_client(String nume_client) {
        this.nume_client = nume_client;
    }

    public void setNume_produs(String nume_produs) {
        this.nume_produs = nume_produs;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "id=" + id +
                ", nume_client='" + nume_client + '\'' +
                ", nume_produs='" + nume_produs + '\'' +
                ", cantitate=" + cantitate +
                '}';
    }
}
