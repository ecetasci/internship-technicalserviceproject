package com.ecetasci.repo;

import com.ecetasci.entity.ServiceLog;

import java.util.List;
import java.util.Optional;

public class ServiceLogRepository extends RepositoryManager<ServiceLog, Integer> {
    public ServiceLogRepository() {
        super();
    }

    @Override
    public ServiceLog save(ServiceLog entity) {
        return super.save(entity);
    }

    @Override
    public Iterable<ServiceLog> saveAll(Iterable<ServiceLog> entities) {
        return super.saveAll(entities);
    }

    @Override
    public boolean deleteById(Integer integer) {
        return super.deleteById(integer);
    }

    @Override
    public ServiceLog update(ServiceLog entity) {
        return super.update(entity);
    }

    @Override
    public Optional<ServiceLog> findById(Integer integer) {
        return super.findById(integer);
    }

    @Override
    public List<ServiceLog> findAll() {
        return super.findAll();
    }

    @Override
    public boolean existsById(Integer integer) {
        return super.existsById(integer);
    }


    public List<ServiceLog> findByCustomerId(String phone) {
        CustomerRepository customerRepository = new CustomerRepository();

        Integer customerId = customerRepository
                .findCustomerIdByPhone(phone);

        if (customerId == null) {
            return java.util.Collections.emptyList();
        }

        List<ServiceLog> serviceLogList = em.createQuery("select s from ServiceLog s WHERE s.customer.id= :customerId", ServiceLog.class)
                .setParameter("customerId", customerId).getResultList();
        return serviceLogList;


    }
}
