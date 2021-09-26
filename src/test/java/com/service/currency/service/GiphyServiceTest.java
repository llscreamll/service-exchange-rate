package com.service.currency.service;


import com.service.currency.dto.GiphyResponseDto;
import com.service.currency.exception.InvalidUrlGifException;
import com.service.currency.feign.GiphyFeign;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;

@SpringBootTest
public class GiphyServiceTest {
    @Autowired
    private GiphyService giphyService;
    @MockBean
    private GiphyFeign giphyFeign;


    @Test
    @DisplayName("When calling the method getGif returns the desired GiphyResponseDto")
    public void test1() {
        Mockito.doReturn(new GiphyResponseDto())
                .when(giphyFeign)
                .getGif(ArgumentMatchers.anyString());
    }

    @Test
    @DisplayName("When calling the method getGif correct data must be returned")
    public void test2() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("image_url", "newUrlGif");
        GiphyResponseDto giphyResponseDto = new GiphyResponseDto();
        giphyResponseDto.setData(data);
        Mockito.doReturn(giphyResponseDto).when(giphyFeign).getGif("rich");

        Assertions.assertEquals("newUrlGif", giphyService.getUrlGif(true));
        Mockito.verify(giphyFeign,Mockito.times(1)).getGif("rich");

    }

    @Test
    @DisplayName("method must return InvalidUrlGifException")
    public void test3() throws InvalidUrlGifException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("img", "newUrlGif");
        GiphyResponseDto giphyResponseDto = new GiphyResponseDto();
        giphyResponseDto.setData(data);
        Mockito.doReturn(giphyResponseDto).when(giphyFeign).getGif("rich");

        Assertions.assertThrows(InvalidUrlGifException.class, ()-> giphyService.getUrlGif(true));
    }



}
