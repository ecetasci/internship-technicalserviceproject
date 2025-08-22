package com.ecetasci.repo;


import com.ecetasci.entity.Customer;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class CustomerRepository extends RepositoryManager<Customer, Integer> {

    public CustomerRepository() {
        super(); // Mevcut RepositoryManager ctor’unu kullan
    }

    @Override
    public Optional<Customer> findById(Integer id) {

        return Optional.ofNullable(em.find(Customer.class, id));
    }

    @Override
    public List<Customer> findAll() {
        return em.createQuery("select c from Customer c order by c.name", Customer.class)
                .getResultList();
    }

    @Override
    public boolean deleteById(Integer id) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Customer c = em.find(Customer.class, id);
            if (c != null) {
                em.remove(c);
                tx.commit();
                return true;
            }
            tx.rollback();
            return false;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            return false;
        }
    }

    @Override
    public boolean existsById(Integer id) {

        return em.find(Customer.class, id) != null;
    }


    public List<Customer> searchByName(String keyword) {
        return em.createQuery(
                        "select c from Customer c " +
                                "where lower(c.name) like lower(concat('%', :kw, '%')) " +
                                "order by c.name", Customer.class)
                .setParameter("kw", keyword)
                .getResultList();
    }

    public Optional<Customer> findByEmail(String email) {
        var list = em.createQuery(
                        "select c from Customer c where c.email = :email", Customer.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public Optional<Integer> findCustomerIdByPhone(String customerPhone) {

        List<Integer> resultList = em.createQuery(
                        "select c.id from Customer c " +
                                "where lower(c.phone) = lower(:customerPhone)", Integer.class)
                .setParameter("customerPhone", customerPhone)
                .getResultList();
        return resultList.stream().findAny();

    }


}

