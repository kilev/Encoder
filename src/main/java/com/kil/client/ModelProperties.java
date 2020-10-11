package com.kil.client;

import lombok.Data;

import java.util.List;

@Data
public class ModelProperties {
    private ValueStreamProperties inputPartsProperties;
    private List<MachineToolProperties> machineToolProperties;
}
