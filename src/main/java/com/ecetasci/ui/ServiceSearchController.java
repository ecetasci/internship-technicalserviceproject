package com.ecetasci.ui;

import com.ecetasci.entity.Customer;
import com.ecetasci.entity.ServiceLog;
import com.ecetasci.repo.CustomerRepository;
import com.ecetasci.repo.ServiceLogRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Telefon numarasıyla müşteri ve servis kayıtlarını arayan ekranın controller’ı.
 */
public class ServiceSearchController {

    private CustomerRepository customerRepo;
    private ServiceLogRepository serviceLogRepo;

    public void setRepositories(CustomerRepository cr, ServiceLogRepository slr) {
        this.customerRepo = cr;
        this.serviceLogRepo = slr;
    }

    @FXML private TextField phoneField;
    @FXML private Label lblCustomerName;
    @FXML private Label lblCustomerPhone;
    @FXML private Label lblInfo;

    @FXML private TableView<ServiceLog> table;
    @FXML private TableColumn<ServiceLog, Integer> colId;
    @FXML private TableColumn<ServiceLog, String> colDevice;
    @FXML private TableColumn<ServiceLog, String> colStatus;
    @FXML private TableColumn<ServiceLog, LocalDateTime> colEntry;
    @FXML private TableColumn<ServiceLog, LocalDateTime> colExit;
    @FXML private TableColumn<ServiceLog, String> colDuration;

    private final ObservableList<ServiceLog> rows = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        table.setItems(rows);

        // ServiceLog entity içinde getId(), getDeviceName(), getStatus(), getEntryDate(), getExitDate(), getDurationText() gibi getter’ların olması gerekiyor
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDevice.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEntry.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        colExit.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationText"));

        phoneField.setOnAction(this::onSearch); // Enter ile ara
        lblInfo.setText("Telefon gir ve Enter'a bas");
    }

    @FXML
    public void onSearch(ActionEvent e) {
        String phone = phoneField.getText() == null ? "" : phoneField.getText().trim();
        if (phone.isBlank()) {
            info("Telefon giriniz.");
            phoneField.requestFocus();
            return;
        }
        if (customerRepo == null || serviceLogRepo == null) {
            error("Bağlantı hatası: repository’ler set edilmemiş");
            return;
        }

        try {
            Integer cid = customerRepo.findCustomerIdByPhone(phone);
            if (cid == null || cid == 0) {
                lblCustomerName.setText("-");
                lblCustomerPhone.setText(phone);
                rows.clear();
                info("Bu telefona ait müşteri bulunamadı.");
                return;
            }

            Optional<Customer> optionalCustomer = customerRepo.findById(cid);
            optionalCustomer.ifPresentOrElse(c -> {
                lblCustomerName.setText(blankTo(c.getName(), "(isimsiz)"));
                lblCustomerPhone.setText(blankTo(c.getPhone(), phone));
            }, () -> {
                lblCustomerName.setText("-");
                lblCustomerPhone.setText(phone);
            });

            // kayıtları getir
            List<ServiceLog> logs = serviceLogRepo.findByCustomerId(phone);

            rows.clear();
            rows.addAll(logs);

            info(logs.isEmpty() ? "Servis kaydı yok." : logs.size() + " kayıt bulundu.");
        } catch (Exception ex) {
            error("Arama başarısız: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String blankTo(String v, String fb) { return (v == null || v.isBlank()) ? fb : v; }
    private void info(String msg){ lblInfo.setStyle(""); lblInfo.setText(msg); }
    private void error(String msg){ lblInfo.setStyle("-fx-text-fill:#d33;"); lblInfo.setText(msg); }
}
