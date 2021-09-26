package com.service.currency.feign;
import com.service.currency.dto.OpenChangeRatesResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchangeRate",url = "${feign.openexchangerates.url}")
public interface OpenExchangeRateFeign {
    @GetMapping("api/historical/{date}.json?app_id=${feign.openexchangerates.you-api}&symbols=${currency}%2C{symbol}")
   OpenChangeRatesResponseDto getExchangeRate(@PathVariable("date")String date,
                                              @PathVariable("symbol")String symbol);
}
