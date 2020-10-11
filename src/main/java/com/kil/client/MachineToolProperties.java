package com.kil.client;

import lombok.Data;

@Data
public class MachineToolProperties {
    private ValueStreamProperties toolProperties;
    private ValueStreamProperties queueProperties;
}
