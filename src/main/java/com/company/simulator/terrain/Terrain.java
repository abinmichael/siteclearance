package com.company.simulator.terrain;

public abstract class Terrain {
    private boolean visited = false;

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public abstract int getFuelCost();

}