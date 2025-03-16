package Dao;

import ModelClasses.Comanda;

import java.util.List;


public class ComandaDAO extends AbstractionDAO<Comanda> {
    public ComandaDAO() {
        super();
    }

    @Override
    public List<Comanda> findAll() {
        return super.findAll();
    }

    @Override
    public Comanda findById(int id) {
        return super.findById(id);
    }

    @Override
    public Comanda insert(Comanda comanda) {
        return super.insert(comanda);
    }

    @Override
    public Comanda update(Comanda comanda) {
        return super.update(comanda);
    }

    @Override
    public Comanda delete(Comanda comanda) {
        return super.delete(comanda);
    }
}
