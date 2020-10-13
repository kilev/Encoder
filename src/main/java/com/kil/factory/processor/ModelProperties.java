package com.kil.factory.processor;

import com.kil.stream.RandomDurationStream;
import lombok.Data;

import java.util.List;

@Data
public class ModelProperties {
    private RandomDurationStream inputPartsStream;
    private List<Processor> processorProperties;
}
