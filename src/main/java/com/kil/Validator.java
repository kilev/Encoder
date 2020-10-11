package com.kil;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.util.BigReal;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Validator {

    public void validate(Model model) throws ModelException {
        validateTransitionProbabilityMatrix(model.getTransitionProbabilityMatrix());
        validateVectorProbabilitySum(model.getInitialStatesVector());
        validateIterationsCount(model.getIterationsCount());
    }

    private void validateTransitionProbabilityMatrix(FieldMatrix<BigReal> matrix) throws ModelException {
        List<FieldVector<BigReal>> rows = Stream.iterate(0, value -> value++)
                .limit(matrix.getColumnDimension())
                .map(matrix::getRow)
                .map(MatrixUtils::createFieldVector)
                .collect(Collectors.toList());

        for (FieldVector<BigReal> row : rows) {
            validateVectorProbabilitySum(row);
        }
    }

    private void validateVectorProbabilitySum(FieldVector<BigReal> vector) throws ModelException {
        BigReal sum = List.of(vector.toArray()).stream()
                .reduce(BigReal.ZERO, BigReal::add);

        if (sum.bigDecimalValue().compareTo(BigDecimal.ONE) != 0) {
            throw new ModelException("Некорректно задан вектор вероятностей. Сумма вероятностей не может быть отлична от единицы");
        }
    }

    private void validateIterationsCount(int iterationsCount) throws ModelException {
        if (iterationsCount < 1) {
            throw new ModelException("Некорректно задано количество циклов. Число не может быть меньше 1");
        }
    }


}
