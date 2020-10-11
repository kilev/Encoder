package com.kil;

import lombok.Data;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.util.BigReal;

@Data
public class ComputedModel {
    private FieldVector<BigReal> probability;
}
