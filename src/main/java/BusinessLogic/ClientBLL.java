package BusinessLogic;
import ModelClasses.Client;
import Dao.ClientDAO;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

public class ClientBLL {
    private ClientDAO clientDAO;
    public Client findClientById(int id)
    {
        Client cl = clientDAO.findById(id);
        if (cl == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return cl;
    }

    public List<Client> findAll()
    {
        List<Client> clienti = new ArrayList<>();
        clienti = clientDAO.findAll();
        return clienti;
    }

    public void insert(Client c)
    {
        clientDAO.insert(c);
    }

    public void update(Client c)
    {
        clientDAO.update(c);
    }

    public void delete(Client c)
    {
        clientDAO.delete(c);
    }
}
