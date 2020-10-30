package com.kil;

import com.google.common.collect.Collections2;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

@UtilityClass
public class Utils {

    public <T, R> boolean hasDuplicates(Collection<T> collection, Function<? super T, R> transformFunction) {
        return hasDuplicates(Collections2.transform(collection, transformFunction::apply));
    }

    public <R> boolean hasDuplicates(Collection<R> collections) {
        return new HashSet<>(collections).size() != collections.size();
    }

    public <T, R> boolean notContains(Collection<T> collection, Function<? super T, R> transformFunction, R expectedValue) {
        return !contains(collection, transformFunction, expectedValue);
    }

    public <T, R> boolean contains(Collection<T> collection, Function<? super T, R> transformFunction, R expectedValue) {
        return Collections2.transform(collection, transformFunction::apply).contains(expectedValue);
    }

}
