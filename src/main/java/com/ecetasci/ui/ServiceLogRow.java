package com.ecetasci.ui;

import com.ecetasci.entity.Device;
import com.ecetasci.entity.ServiceLog;
import com.ecetasci.service.LogService;
import com.ecetasci.util.ServiceTimeUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** TableView için basit satır model: alanlar + getter'lar. */
public class ServiceLogRow {
    private long id;
    private String device;     // cihaz adı/modeli
    private String status;     // durum metni
    private String entry;      // giriş tarihi (metin)
    private String exit;       // çıkış tarihi (metin)
    private String duration;   // süre (metin)

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ServiceLogRow(long id, String device, String status, String entry, String exit, String duration) {
        this.id = id;
        this.device = device;
        this.status = status;
        this.entry = entry;
        this.exit = exit;
        this.duration = duration;
    }

      // --- getter'lar (PropertyValueFactory bunları okur) ---
    public long getId() { return id; }
    public String getDevice() { return device; }
    public String getStatus() { return status; }
    public String getEntry() { return entry; }
    public String getExit() { return exit; }
    public String getDuration() { return duration; }
}
