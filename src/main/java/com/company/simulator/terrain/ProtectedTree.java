package com.company.simulator.terrain;

public class ProtectedTree extends Terrain {
    @Override
    public int getFuelCost() {
        return 0;  // Cannot be cleared
    }

}
