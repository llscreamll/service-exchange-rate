package com.service.currency.feign;

import com.service.currency.dto.GiphyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gif",url = "${feign.giphy.url}")
public interface GiphyFeign {
    @GetMapping("v1/gifs/random?tag={tags}&rating=g&api_key=${feign.giphy.you-api}")
    GiphyResponseDto getGif(@PathVariable("tags") String tags);
}

