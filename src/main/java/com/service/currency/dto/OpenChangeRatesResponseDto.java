package com.service.currency.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class OpenChangeRatesResponseDto {

    private String disclaimer;
    private String license;
    private LocalDate timestamp;
    private String base;
    private HashMap<String, BigDecimal> rates;

    public OpenChangeRatesResponseDto() {
    }

    public OpenChangeRatesResponseDto(String disclaimer, String license, LocalDate timestamp, String base, HashMap<String, BigDecimal> rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public HashMap<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, BigDecimal> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "ExchangeRatesDto{" +
                "disclaimer='" + disclaimer + '\'' +
                ", license='" + license + '\'' +
                ", timestamp=" + timestamp +
                ", base='" + base + '\'' +
                ", rates=" + rates +
                '}';
    }
}
