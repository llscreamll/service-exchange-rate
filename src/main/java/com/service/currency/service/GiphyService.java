package com.service.currency.service;

import com.service.currency.dto.GiphyResponseDto;
import com.service.currency.exception.InvalidApiKeyGiphyException;
import com.service.currency.exception.InvalidUrlGifException;
import com.service.currency.feign.GiphyFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GiphyService {
    Logger logger = LoggerFactory.getLogger(GiphyService.class);
    private final GiphyFeign giphyFeign;

    @Value("${feign.param.giphy.success}")
    private String success;
    @Value("${feign.param.giphy.not-success}")
    private String notSuccess;

    @Autowired
    public GiphyService(GiphyFeign giphyFeign) {
        this.giphyFeign = giphyFeign;
    }

    public String getUrlGif(boolean resultExchangeRate) {
        String tag = resultExchangeRate ? success : notSuccess;
        GiphyResponseDto giphyResponseDto;
        try {
            giphyResponseDto = giphyFeign.getGif(tag);
            if (giphyResponseDto.getData().get("image_url") == null) {
                throw new InvalidUrlGifException();
            }
            return giphyResponseDto.getData().get("image_url").toString();
        } catch (InvalidUrlGifException e) {
            logger.error("Invalid url for giphy");
            throw new InvalidUrlGifException();
        } catch (Exception e) {
            logger.error("Invalid api key for giphy");
            throw new InvalidApiKeyGiphyException();
        }
    }
}
