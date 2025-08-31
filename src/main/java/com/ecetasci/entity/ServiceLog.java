package com.ecetasci.entity;

import com.ecetasci.util.ServiceTimeUtil;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_log")
public class ServiceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", nullable = false)
    private Technician technician;

    @Column(columnDefinition = "text")
    private String complaint;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "estimated_delivery")
    private LocalDateTime estimatedDelivery;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    // ServiceLog.java
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false) // service_log.customer_id
    private Customer customer;


    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "ServiceLog{" +
                "id=" + id +
                ", device=" + device +
                ", technician=" + technician +
                ", complaint='" + complaint + '\'' +
                ", status=" + status +
                ", entryDate=" + entryDate +
                ", estimatedDelivery=" + estimatedDelivery +
                ", deliveryDate=" + deliveryDate +
                ", customer=" + customer +
                '}';
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public String getDeviceName(){
          return device!=null ? device.getModel() : "model girilmemiştir";
    }

    public String getDurationText() {
        if (entryDate == null) return "-";
        Duration d = Duration.between(entryDate, LocalDate.now());
        return "a";
       // return ServiceTimeUtil.formatDuration(d);
    }
    public void setDevice(Device device) {
        this.device = device;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
