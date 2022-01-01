package com.aiebt.ai.backtracking;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSearchTest {

    @Test
    public void itWorks() {
        Optional<DummyProblem> result = new BacktrackingSearch().execute(new DummyProblem());
        assertTrue(result.isPresent());
        assertTrue(result.get().isSolved());
    }

    private static class DummyProblem implements Problem<Boolean, Boolean> {

        private final int i;
        private final List<Boolean> booleans;

        DummyProblem() {
            this(List.of(false, false), 0);
        }

        private DummyProblem(List<Boolean> booleans, int i) {
            this.booleans = booleans;
            this.i = i;
        }

        @Override
        public boolean isSolved() {
            return booleans.stream().filter(b -> b).count() == booleans.size();
        }

        @Override
        public Boolean getNextUnassignedVariable() {
            return booleans.stream().filter(b -> !b).findFirst().orElseThrow();
        }

        @Override
        public Collection<Boolean> getPossibleValuesForVariable(Boolean aBoolean) {
            return List.of(true);
        }

        @Override
        @SuppressWarnings("unchecked")
        public Optional<DummyProblem> defineVariable(Boolean aBoolean, Boolean aBoolean2) {
            return Optional.of(
                    new DummyProblem(
                            List.of(
                                    i == 0 ? aBoolean2 : booleans.get(0),
                                    i == 1 ? aBoolean2 : booleans.get(1)
                            ),
                            i + 1
                    )
            );
        }
    }
}
