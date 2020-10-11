package com.kil;

import lombok.Data;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.util.BigReal;

@Data
public class Model {
    private FieldMatrix<BigReal> transitionProbabilityMatrix;
    private FieldVector<BigReal> initialStatesVector;
    private int iterationsCount;
}
