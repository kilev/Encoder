package com.kil;

import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;

@UtilityClass
public class Computer {

    @Nonnull
    public ComputedModel compute(@Nonnull Model model) {
        ComputedModel computedModel = new ComputedModel();
        computedModel.setProbability(model.getTransitionProbabilityMatrix()
                .power(model.getIterationsCount())
                .preMultiply(model.getInitialStatesVector()));

        return computedModel;
    }
}
