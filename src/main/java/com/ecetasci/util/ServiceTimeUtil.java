package com.ecetasci.util;

import java.time.Duration;

public class ServiceTimeUtil {


    public static String formatDuration(Duration duration) {
        if (duration == null) {
            return "-";
        }

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append(" gün ");
        if (hours > 0) sb.append(hours).append(" saat ");
        if (minutes > 0) sb.append(minutes).append(" dakika ");
        if (seconds > 0) sb.append(seconds).append(" saniye");

        return sb.toString().trim();
    }



}
