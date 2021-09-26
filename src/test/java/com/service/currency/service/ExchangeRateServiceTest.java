package com.service.currency.service;

import com.service.currency.dto.OpenChangeRatesResponseDto;
import com.service.currency.feign.OpenExchangeRateFeign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@SpringBootTest
public class ExchangeRateServiceTest {
    @Autowired
    private ExchangeRateService exchangeRateService;
    @MockBean
    private OpenExchangeRateFeign openExchangeRateFeign;



    @Test
    @DisplayName("Method getExchangeRatesRequestDto should return ExchangeRatesRequestDto")
    public void test1() {
        Mockito.doReturn(new OpenChangeRatesResponseDto())
                .when(openExchangeRateFeign)
                .getExchangeRate(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("OpenExchangeRateFeign must call with the correct parameters passed in exchangeRateService")
    public void test2() {
        HashMap<String, BigDecimal> rates = new HashMap<>();
        rates.put("RUB", new BigDecimal(75));
        rates.put("OAO", new BigDecimal(30));
        OpenChangeRatesResponseDto openChangeRatesResponseDto = new OpenChangeRatesResponseDto();
        openChangeRatesResponseDto.setRates(rates);
        String utc = LocalDate.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Mockito.doReturn(openChangeRatesResponseDto).when(openExchangeRateFeign).getExchangeRate(utc, "OAO");
        exchangeRateService.getExchangeRatesRequestDto(utc, "OAO");
        Mockito.verify(openExchangeRateFeign, Mockito.times(1))
                .getExchangeRate(utc, "OAO");
    }



    @Test
    @DisplayName("getRelationToTheRub should correctly calculate difference")
    public void test3() {
        OpenChangeRatesResponseDto testExchangeYesterday = new OpenChangeRatesResponseDto();
        testExchangeYesterday.setRates(
                new HashMap<String, BigDecimal>() {{
                    put("RUB", new BigDecimal(70));
                    put("AOA", new BigDecimal(50));
                }});

        OpenChangeRatesResponseDto testExchangeToday = new OpenChangeRatesResponseDto();
        testExchangeToday.setRates(
                new HashMap<String, BigDecimal>() {{
                    put("RUB", new BigDecimal(72));
                    put("AOA", new BigDecimal(50));
                }});

        BigDecimal yesterday = exchangeRateService.getRelationToTheRub(testExchangeYesterday, "AOA").setScale(3, RoundingMode.UP);
        BigDecimal today = exchangeRateService.getRelationToTheRub(testExchangeToday, "AOA").setScale(3, RoundingMode.UP);

        Assertions.assertEquals(yesterday, new BigDecimal("1.400"));
        Assertions.assertEquals(today, new BigDecimal("1.440"));
        Assertions.assertTrue(today.compareTo(yesterday) > 0);
        Assertions.assertNotEquals(0, today.compareTo(yesterday));
    }

}