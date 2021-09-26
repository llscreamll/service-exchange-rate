package com.service.currency.controller;

import com.service.currency.exception.InvalidParamException;
import com.service.currency.service.ExchangeRateService;
import com.service.currency.service.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class ExchangeRateController {
    @Value("${show.gif.to.html}")
    private boolean showGif;
    private final ExchangeRateService exchangeRateService;
    private final GiphyService giphyService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService, GiphyService giphyService) {
        this.exchangeRateService = exchangeRateService;
        this.giphyService = giphyService;
    }

    @GetMapping(value = "code/{symbol}")
    public ResponseEntity<String> checkExchangeRate(@PathVariable(name = "symbol", required = false) String symbol) {
        if (symbol.length() != 3) {
            throw new InvalidParamException();
        }
        String urlGif = giphyService.getUrlGif(exchangeRateService.checkRelation(symbol.toUpperCase()));

        return showGif ? ResponseEntity.ok("<body align ='center'><img src=" + urlGif + "></body>") : ResponseEntity.ok(urlGif);
    }

}


