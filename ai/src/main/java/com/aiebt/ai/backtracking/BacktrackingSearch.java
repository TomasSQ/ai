package com.aiebt.ai.backtracking;

import java.util.Collection;
import java.util.Optional;

public class BacktrackingSearch {

    public <V, D, P extends Problem<V, D>> Optional<P> execute(P problem) {
        if (problem.isSolved()) {
            return Optional.of(problem);
        }

        V variable = problem.getNextUnassignedVariable();
        Collection<D> possibleValues = problem.getPossibleValuesForVariable(variable);

        for (D value : possibleValues) {
            Optional<P> next = problem.defineVariable(variable, value);
            if (next.isEmpty()) {
                continue;
            }
            Optional<P> solve = execute(next.get());
            if (solve.isPresent()) {
                return solve;
            }
        }
        return Optional.empty();
    }
}
