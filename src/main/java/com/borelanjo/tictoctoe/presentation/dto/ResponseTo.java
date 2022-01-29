package com.borelanjo.tictoctoe.presentation.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public final class ResponseTo<T> {

    private T data;
    private List<String> errors = new ArrayList<>();
    private List<String> links = new ArrayList<>();

    public ResponseTo(T data) {
        this.data = data;
    }
}
