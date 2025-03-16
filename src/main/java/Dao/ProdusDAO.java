package Dao;

import ModelClasses.Produs;

import java.util.List;


public class ProdusDAO extends AbstractionDAO<Produs> {
    public ProdusDAO() {
        super();
    }

    @Override
    public List<Produs> findAll() {
        return super.findAll();
    }

    @Override
    public Produs findById(int id) {
        return super.findById(id);
    }

    @Override
    public Produs insert(Produs produs) {
        return super.insert(produs);
    }

    @Override
    public Produs update(Produs produs) {
        return super.update(produs);
    }

    @Override
    public Produs delete(Produs produs) {
        return super.delete(produs);
    }

    @Override
    public Produs findByName(String name) {
        return super.findByName(name);
    }
}
