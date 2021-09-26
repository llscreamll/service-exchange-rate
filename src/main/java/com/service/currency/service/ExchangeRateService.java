package com.service.currency.service;

import com.service.currency.dto.OpenChangeRatesResponseDto;
import com.service.currency.exception.InvalidApiKeyChangeRatesException;
import com.service.currency.exception.InvalidParamException;
import com.service.currency.feign.OpenExchangeRateFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class ExchangeRateService {
    Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);
    @Value("${currency}")
    private String currency;

    private final OpenExchangeRateFeign openExchangeRateFeign;

    @Autowired
    public ExchangeRateService(OpenExchangeRateFeign openExchangeRateFeign) {
        this.openExchangeRateFeign = openExchangeRateFeign;
    }

    public Boolean checkRelation(String symbol) {
//     info:  two queries because one can only be on a paid basis (Developer ,Enterprise ,Unlimited)
        OpenChangeRatesResponseDto resultExchangeRateForToday = getExchangeRatesRequestDto(LocalDate.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), symbol);
        OpenChangeRatesResponseDto resultExchangeRateForYesterday = getExchangeRatesRequestDto(LocalDate.now(ZoneId.of("UTC")).minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), symbol);
        BigDecimal rateForToday = getRelationToTheRub(resultExchangeRateForToday, symbol);
        BigDecimal rateForYesterday = getRelationToTheRub(resultExchangeRateForYesterday, symbol);
        return rateForToday.compareTo(rateForYesterday) >= 0;
    }

    protected OpenChangeRatesResponseDto getExchangeRatesRequestDto(String date, String symbol) {
        OpenChangeRatesResponseDto exchange;
        try {
            exchange = openExchangeRateFeign.getExchangeRate(date, symbol);
            if (exchange.getRates().get(symbol) == null || exchange.getRates().get(currency) == null) {
                throw new InvalidParamException();
            }
            return exchange;
        } catch (InvalidParamException e) {
            logger.error("Invalid param exception");
            throw new InvalidParamException();
        } catch (Exception e) {
            logger.error("Not the correct key for openexchangerates");
            throw new InvalidApiKeyChangeRatesException();
        }
    }
    protected BigDecimal getRelationToTheRub(OpenChangeRatesResponseDto openChangeRatesResponseDto, String symbol) {
        BigDecimal exchangeRateForRub = openChangeRatesResponseDto.getRates().get(currency);
        BigDecimal exchangeForRate = openChangeRatesResponseDto.getRates().get(symbol);
        return exchangeRateForRub.divide(exchangeForRate, 6, RoundingMode.HALF_UP);
    }

}


