package com.aiebt.ai.backtracking;

import java.util.Collection;
import java.util.Optional;

public interface Problem<V, D> {

    boolean isSolved();

    V getNextUnassignedVariable();
    Collection<D> getPossibleValuesForVariable(V v);
    <P extends Problem<V, D>> Optional<P> defineVariable(V v, D d);
}
