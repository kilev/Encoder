package com.kil;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.Map;

public class LicenseService {
    private static final Map<String, String> LICENCE_ENV_MAP = ImmutableMap.of(
            "COMPUTERNAME", "LAPTOP-1EH75PC2",
            "PROCESSOR_IDENTIFIER", "Intel64 Family 6 Model 158 Stepping 10, GenuineIntel");
    private static final String ACCESS_KEY = "4f4949a7-fcc0-4a92-bf9f-df30a6e2252a";

    @Getter
    private boolean licenseActivated = false;

    public boolean isValidHardWare() {
        return LICENCE_ENV_MAP.entrySet().stream()
                .allMatch(entry -> System.getenv(entry.getKey()).equals(entry.getValue()));
    }

    public boolean activateLicence(String key) {
        return licenseActivated = key.equals(ACCESS_KEY);
    }
}
