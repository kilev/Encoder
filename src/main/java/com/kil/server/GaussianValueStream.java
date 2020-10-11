package com.kil.server;

import lombok.Data;

import java.util.Random;

@Data
public class GaussianValueStream implements RandomValueStream {
    private static final Random RANDOM = new Random();

    private final int mean;
    private final int sigma;

    @Override
    public double getNextValue() {
        return (sigma * RANDOM.nextGaussian()) + mean;
    }
}
