package com.ecetasci.ui;

import com.ecetasci.entity.*;
import com.ecetasci.repo.CustomerRepository;
import com.ecetasci.repo.ServiceLogRepository;
import com.ecetasci.util.JpaUtility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        seedOneCustomerAndOneService();

        // 1) FXMLLoader oluştur ve FXML yükle
        FXMLLoader fx = new FXMLLoader(Main.class.getResource("/ui/service_search.fxml"));
        Scene scene = new Scene(fx.load());

        // 2) Controller’ı al
        ServiceSearchController controller = fx.getController();

        // 3) Repository örneklerini oluştur
        CustomerRepository cr = new CustomerRepository();
        ServiceLogRepository slr = new ServiceLogRepository();

        // 4) Controller’a ver
        controller.setRepositories(cr, slr);

        // 5) Stage’i ayarla
        stage.setTitle("Service Desk — Search by Phone");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(540);
        stage.show();
    }



    private void seedOneCustomerAndOneService() {
        var em = JpaUtility.getEntityManager();
        var tx = em.getTransaction();
        tx.begin();
        try {
            Long serviceCount = em.createQuery("select count(s) from ServiceLog s", Long.class)
                    .getSingleResult();
            if (serviceCount == 0L) {
                // 1) Müşteri: varsa al, yoksa oluştur
                Customer customer = em.createQuery(
                                "select c from Customer c where c.phone = :p", Customer.class)
                        .setParameter("p", "531")
                        .setMaxResults(1)
                        .getResultStream()
                        .findFirst()
                        .orElseGet(() -> {
                            Customer c = new Customer();
                            c.setName("ece");
                            c.setPhone("531");
                            c.setEmail("ece@example.com");
                            c.setAddress("İstanbul");
                            em.persist(c);
                            return c;
                        });

                // 2) Cihaz: müşteri set EDİLEREK oluşturulmalı
                Device device = new Device();
                device.setBrand("Casper");
                device.setModel("Nirvana");
                device.setSerialNo("cs324245"); // alan yoksa sil
                device.setCustomer(customer);        // <<< ÖNEMLİ
                em.persist(device);
                // Eğer çift yönlü ilişki varsa:
                // customer.getDevices().add(device);
                Technician tech = new Technician();
                tech.setFirstName("Ali Usta");
                em.persist(tech);
                // 3) Servis kaydı
                ServiceLog log = new ServiceLog();
                log.setCustomer(customer);
                log.setDevice(device);
                log.setTechnician(tech);
                log.setComplaint("Casper Nirvana - açılmıyor / aşırı ısınma");
                log.setStatus(Status.KAYDEDILDI);   // string ise "received" yaz
                String entryDateStr = "2025-08-24 10:30:00";
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime entry = LocalDateTime.parse(entryDateStr, fmt);

                log.setEntryDate(entry);


                log.setEstimatedDelivery(LocalDateTime.now().plusDays(3));
                em.persist(log);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    public static void main(String[] args) { launch(args); }
}
