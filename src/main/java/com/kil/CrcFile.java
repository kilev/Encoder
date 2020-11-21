package com.kil;

import lombok.Data;

@Data
public class CrcFile {
    private String data;
    private EncodingType encodingType;
    private EncodingMethod encodingMethod;
}
