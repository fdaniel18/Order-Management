package BusinessLogic;
import Dao.ClientDAO;
import ModelClasses.Client;
import ModelClasses.Comanda;
import Dao.ComandaDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ComandaBLL {

    private ComandaDAO comandaDAO;
    public Comanda findComandaById(int id)
    {
        Comanda co = comandaDAO.findById(id);
        if (co == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return co;
    }

    public List<Comanda> findAll()
    {
        List<Comanda> comenzi = new ArrayList<>();
        comenzi = comandaDAO.findAll();
        return comenzi;
    }

    public void insert(Comanda c)
    {
        comandaDAO.insert(c);
    }

    public void update(Comanda c)
    {
        comandaDAO.update(c);
    }

    public void delete(Comanda c)
    {
        comandaDAO.delete(c);
    }
}
