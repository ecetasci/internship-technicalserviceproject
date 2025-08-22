package com.ecetasci.repo;

import com.ecetasci.ui.ServiceDeskController;

import java.util.List;
import java.util.Optional;

public class TechnicianRepository extends RepositoryManager<ServiceDeskController,Integer>{

    public TechnicianRepository() {
        super();
    }

    @Override
    public ServiceDeskController save(ServiceDeskController entity) {
        return super.save(entity);
    }

    @Override
    public Iterable<ServiceDeskController> saveAll(Iterable<ServiceDeskController> entities) {
        return super.saveAll(entities);
    }

    @Override
    public boolean deleteById(Integer integer) {
        return super.deleteById(integer);
    }

    @Override
    public ServiceDeskController update(ServiceDeskController entity) {
        return super.update(entity);
    }

    @Override
    public Optional<ServiceDeskController> findById(Integer integer) {
        return super.findById(integer);
    }

    @Override
    public List<ServiceDeskController> findAll() {
        return super.findAll();
    }
}
