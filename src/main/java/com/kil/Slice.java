package com.kil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Slice {
    private Double alphaLevel;
    private Double low;
    private Double high;

    public Slice(Double alphaLevel){
        this.alphaLevel = alphaLevel;
    }
}
