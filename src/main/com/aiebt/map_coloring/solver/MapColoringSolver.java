package com.aiebt.map_coloring.solver;

import com.aiebt.ai.backtracking.BacktrackingSearch;
import com.aiebt.map_coloring.core.Country;

public class MapColoringSolver {

    private final Country country;
    private final BacktrackingSearch backtrackingSearch;

    public MapColoringSolver(Country country) {
        this.country = country;
        backtrackingSearch = new BacktrackingSearch();
    }

    public Country execute() {
        MapColoringProblem mapColoringProblem = new MapColoringProblem(country);
        MapColoringProblem coloringSolved = backtrackingSearch.execute(mapColoringProblem)
                .orElseThrow(() -> new RuntimeException("Can't color map"));
        return coloringSolved.get();
    }
}
