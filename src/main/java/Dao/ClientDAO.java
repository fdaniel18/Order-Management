package Dao;

import ModelClasses.Client;

import java.util.List;


public class ClientDAO extends AbstractionDAO<Client> {
    public ClientDAO() {
        super();
    }

    @Override
    public List<Client> findAll() {
        return super.findAll();
    }

    @Override
    public Client findById(int id) {
        return super.findById(id);
    }

    @Override
    public Client insert(Client client) {
        return super.insert(client);
    }

    @Override
    public Client update(Client client) {
        return super.update(client);
    }

    @Override
    public Client delete(Client client) {
        return super.delete(client);
    }
}
