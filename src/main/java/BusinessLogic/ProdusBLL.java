package BusinessLogic;
import Dao.ClientDAO;
import ModelClasses.Client;
import ModelClasses.Produs;
import Dao.ProdusDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProdusBLL {
    private ProdusDAO produsDAO;
    public Produs findClientById(int id)
    {
        Produs p = produsDAO.findById(id);
        if (p == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return p;
    }

    public Produs findProdusByName(String name)
    {
        Produs p = produsDAO.findByName(name);
        if (p == null) {
            throw new NoSuchElementException("The client with id =" + name + " was not found!");
        }
        return p;
    }

    public List<Produs> findAll()
    {
        List<Produs> produse = new ArrayList<>();
        produse = produsDAO.findAll();
        return produse;
    }

    public void insert(Produs p)
    {
        produsDAO.insert(p);
    }

    public void update(Produs p)
    {
        produsDAO.update(p);
    }

    public void delete(Produs p)
    {
        produsDAO.delete(p);
    }



}
