package com.ecetasci;

import com.ecetasci.service.LogService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        LocalDateTime l = LocalDateTime.of(2025, 8, 21, 10, 15, 30);
        LogService.calDurationUntilNow(l);
    }
}