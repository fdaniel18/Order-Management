package ModelClasses;

public class Produs {
    public int id;
    public String name;
    public int price;
    public int cantitate;

    public Produs(int id, String name, int price, int cantitate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cantitate = cantitate;
    }

    public Produs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", cantitate=" + cantitate +
                '}';
    }
}
