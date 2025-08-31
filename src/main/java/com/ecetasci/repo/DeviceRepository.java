package com.ecetasci.repo;

import com.ecetasci.entity.Device;

import java.util.List;
import java.util.Optional;

public class DeviceRepository extends RepositoryManager<Device, Integer> {

    private final CustomerRepository customerRepository;

    public DeviceRepository(CustomerRepository customerRepository) {
        super();
        this.customerRepository = customerRepository;
    }

    @Override
    public Device save(Device entity) {
        return super.save(entity);
    }

    @Override
    public Iterable<Device> saveAll(Iterable<Device> entities) {
        return super.saveAll(entities);
    }

    @Override
    public boolean deleteById(Integer integer) {
        return super.deleteById(integer);
    }

    @Override
    public Device update(Device entity) {
        return super.update(entity);
    }

    @Override
    public Optional<Device> findById(Integer id) {
        return super.findById(id);
    }

    @Override
    public boolean existsById(Integer integer) {
        return super.existsById(integer);
    }

    @Override
    public List<Device> findAll() {
        return super.findAll();
    }

    public List<Device> findDeviceByCustomerPhone(String customerPhone) {

        Optional<Integer> customerIdByPhone = customerRepository.findCustomerIdByPhone(customerPhone).describeConstable();

        return customerIdByPhone.map(customerId -> em.createQuery("SELECT d From Device d WHERE d.customer.id=: customerId", Device.class)
                .setParameter("customerId", customerId)
                .getResultList()).orElseThrow();
    }
}
