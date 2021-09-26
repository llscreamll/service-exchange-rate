package com.service.currency.dto;

import java.util.HashMap;

public class GiphyResponseDto {
    private HashMap<String, Object> data;

    public GiphyResponseDto(HashMap<String, Object> data) {
        this.data = data;
    }
    public GiphyResponseDto() {
    }
    public HashMap<String, Object> getData() {
        if(data == null){
            data = new HashMap<>();
        }
        return data;
    }
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GiphyRequestDto{" +
                "data=" + data +
                '}';
    }
}
