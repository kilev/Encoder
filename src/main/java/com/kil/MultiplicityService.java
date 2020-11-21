package com.kil;

import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MultiplicityService {
    public static final String MULTIPLICITY_PREFIX = "Множество ";

    public static final double ALPHA_MIN_VALUE = 0.0;
    public static final double ALPHA_MAX_VALUE = 1.0;

    public void prepare(Multiplicity... multiplicities) {
        prepare(Arrays.asList(multiplicities));
    }

    public void prepare(List<Multiplicity> multiplicities) {
        multiplicities.forEach(this::prepare);
    }

    public void prepare(Multiplicity multiplicity) {
        sortByAlphaLevel(multiplicity.getSlices());
    }

    public void validate(List<Multiplicity> multiplicities) {
        multiplicities.forEach(this::validate);
    }

    public void validate(Multiplicity multiplicity) {
        List<Slice> slices = multiplicity.getSlices();

        slices.forEach(slice -> {
            if(slice.getAlphaLevel() < 0d){
                throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
                + ": недопустимое отрицательное значение для альфа-уровня");
            }

//            if(slice.getLow() < 0d){
//                throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
//                        + ": недопустимое отрицательное значение для нижний границы");
//            }
//
//            if(slice.getHigh() < 0d){
//                throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
//                        + ": недопустимое отрицательное значение для верхней границы");
//            }
        });

        if (Utils.hasDuplicates(slices, Slice::getAlphaLevel)) {
            throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
                    + ": содержит повторяющиеся альфа срезы");
        }

        if (!Ordering.natural().isOrdered(Collections2.transform(slices, Slice::getLow))) {
            throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
                    + ": Функция принадлежности не является выпуклой");
        }

        if (!Ordering.natural().reverse().isOrdered(Collections2.transform(slices, Slice::getHigh))) {
            throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
                    + ": Функция принадлежности не является выпуклой");
        }

        if (Utils.notContains(slices, Slice::getAlphaLevel, ALPHA_MIN_VALUE)) {
            throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
                    + ": не содержит среза с обязательным альфа уровнем: " + ALPHA_MIN_VALUE);
        }

        if (Utils.notContains(slices, Slice::getAlphaLevel, ALPHA_MAX_VALUE)) {
            throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
                    + ": не содержит среза с обязательным альфа уровнем: " + ALPHA_MAX_VALUE);
        }

        slices.stream()
                .filter(slice -> slice.getAlphaLevel() == ALPHA_MAX_VALUE)
                .filter(slice -> slice.getLow() > slice.getHigh())
                .forEach(slice -> {
                    throw new MultiplicityValidationException(MULTIPLICITY_PREFIX + multiplicity.getName()
                    + ": Функция принадлежности не является выпуклой");
                });
    }

    public Comparator<Multiplicity> getComparator() {
        return Comparator.comparingDouble(this::calculateAverage);
    }

    private double calculateAverage(Multiplicity multiplicity) {
        List<Slice> slices = multiplicity.getSlices();

        double multiplicitySum = slices.stream()
                .mapToDouble(slice -> slice.getLow() + slice.getHigh())
                .sum();

        return multiplicitySum / slices.size();

    }

    public Multiplicity minus(Multiplicity mul1, Multiplicity mul2) {
        prepareBeforeArithmeticOperations(mul1, mul2);

        List<Slice> resultSlices = mul1.getSlices().stream()
                .map(slice -> {
                    Double alphaLevel = slice.getAlphaLevel();
                    Slice mul2Slice = getByAlphaLevel(mul2.getSlices(), alphaLevel);
                    return new Slice(alphaLevel,
                            slice.getLow() - mul2Slice.getHigh(),
                            slice.getHigh() - mul2Slice.getLow());
                })
                .collect(Collectors.toList());

        return new Multiplicity(MultiplicityName.C, resultSlices);
    }

    public Multiplicity multi(Multiplicity mul1, Multiplicity mul2) {
        prepareBeforeArithmeticOperations(mul1, mul2);

        List<Slice> resultSlices = mul1.getSlices().stream()
                .map(slice -> {
                    Double alphaLevel = slice.getAlphaLevel();
                    Slice mul2Slice = getByAlphaLevel(mul2.getSlices(), alphaLevel);
                    return new Slice(alphaLevel,
                            slice.getLow() * mul2Slice.getLow(),
                            slice.getHigh() * mul2Slice.getHigh());
                })
                .collect(Collectors.toList());

        return new Multiplicity(MultiplicityName.C, resultSlices);
    }

    public Multiplicity plus(Multiplicity mul1, Multiplicity mul2) {
        prepareBeforeArithmeticOperations(mul1, mul2);

        List<Slice> resultSlices = mul1.getSlices().stream()
                .map(slice -> {
                    Double alphaLevel = slice.getAlphaLevel();
                    Slice mul2Slice = getByAlphaLevel(mul2.getSlices(), alphaLevel);
                    return new Slice(alphaLevel,
                            slice.getLow() + mul2Slice.getLow(),
                            slice.getHigh() + mul2Slice.getHigh());
                })
                .collect(Collectors.toList());

        return new Multiplicity(MultiplicityName.C, resultSlices);
    }

    public Multiplicity divide(Multiplicity mul1, Multiplicity mul2) {
        prepareBeforeArithmeticOperations(mul1, mul2);

        List<Slice> resultSlices = mul1.getSlices().stream()
                .map(slice -> {
                    Double alphaLevel = slice.getAlphaLevel();
                    Slice mul2Slice = getByAlphaLevel(mul2.getSlices(), alphaLevel);

                    if (mul2Slice.getHigh() == 0.0 || mul2Slice.getLow() == 0.0) {
                        throw new MultiplicityValidationException("Невозможно выполнить операцию. Границы множества B не могут быть равны 0.");
                    }
                    return new Slice(alphaLevel,
                            slice.getLow() / mul2Slice.getHigh(),
                            slice.getHigh() / mul2Slice.getLow());
                })
                .collect(Collectors.toList());

        return new Multiplicity(MultiplicityName.C, resultSlices);

    }

    private Slice getByAlphaLevel(List<Slice> slices, Double alphaLevel) {
        return slices.stream()
                .filter(slice -> slice.getAlphaLevel().equals(alphaLevel))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("alphaLevel " + alphaLevel + " is not exist in slices: " + slices));
    }

    private void prepareBeforeArithmeticOperations(Multiplicity mul1, Multiplicity mul2) {
        prepare(mul1, mul2);
        calculateMissAlphaLevels(mul1, toAlphaLevels(mul2.getSlices()));
        calculateMissAlphaLevels(mul2, toAlphaLevels(mul1.getSlices()));
    }

    private List<Double> toAlphaLevels(List<Slice> slices) {
        return slices.stream()
                .map(Slice::getAlphaLevel)
                .collect(Collectors.toList());
    }

    private void calculateMissAlphaLevels(Multiplicity multiplicity, List<Double> alphaLevels) {
        Set.copyOf(alphaLevels).forEach(alphaLevel -> calculateMissAlphaLevel(multiplicity, alphaLevel));
    }

    private void calculateMissAlphaLevel(Multiplicity multiplicity, Double alphaLevel) {
        List<Slice> slices = multiplicity.getSlices();

        if (Utils.contains(slices, Slice::getAlphaLevel, alphaLevel)) {
            return;
        }

        Slice missedSlice = new Slice(alphaLevel);
        slices.add(missedSlice);
        sortByAlphaLevel(slices);

        int missedSliceIndex = slices.indexOf(missedSlice);
        Slice previousSlice = slices.get(missedSliceIndex - 1);
        Point previousSliceLowPoint = new Point(previousSlice.getLow(), previousSlice.getAlphaLevel());
        Point previousSliceHighPoint = new Point(previousSlice.getHigh(), previousSlice.getAlphaLevel());

        Slice nextSlice = slices.get(missedSliceIndex + 1);
        Point nextSliceLowPoint = new Point(nextSlice.getLow(), nextSlice.getAlphaLevel());
        Point nextSliceHighPoint = new Point(nextSlice.getHigh(), nextSlice.getAlphaLevel());

//        missedSlice.setHigh((previousSlice.getHigh() + nextSlice.getHigh()) / 2);
//        missedSlice.setLow((previousSlice.getLow() + nextSlice.getLow()) / 2);
        missedSlice.setLow(calculateXValueByStraightLineEquation(previousSliceLowPoint, nextSliceLowPoint, missedSlice.getAlphaLevel()));
        missedSlice.setHigh(calculateXValueByStraightLineEquation(previousSliceHighPoint, nextSliceHighPoint, missedSlice.getAlphaLevel()));
    }

    private double calculateXValueByStraightLineEquation(Point firstPoint, Point secondPoint, double yValue) {
//        double a = secondPoint.getY() - firstPoint.getY();
//        double b = firstPoint.getX() - secondPoint.getX();
//        double c = a * firstPoint.getX() + b * firstPoint.getY();

        return (((yValue - firstPoint.getY()) * (secondPoint.getX() - firstPoint.getX())) / (secondPoint.getY() - firstPoint.getY())) + firstPoint.getX();
    }

    private void sortByAlphaLevel(List<Slice> slices) {
        slices.sort(Comparator.comparing(Slice::getAlphaLevel));
    }
}
