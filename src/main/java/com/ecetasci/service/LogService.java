package com.ecetasci.service;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.ecetasci.util.ServiceTimeUtil.formatDuration;

public class LogService {

    public static String calDurationUntilNow(LocalDateTime entryDate) {

        if (entryDate != null) {
            System.out.println("Serviste geçen süre " + formatDuration(Duration.between(entryDate, LocalDateTime.now())));
            return "Serviste geçen süre" + formatDuration(Duration.between(entryDate, LocalDateTime.now()));

        } else {
            return null;
        }
    }
}
