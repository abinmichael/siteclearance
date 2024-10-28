package com.company.simulator.terrain;

public class RockyLand extends Terrain {
    @Override
    public int getFuelCost() {
        return isVisited() ? 1 : 2;
    }

}
