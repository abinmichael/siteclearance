package com.company.simulator.terrain;

public class TerrainFactory {
    public static Terrain createTerrain(char type) {
        switch (type) {
            case 'o':
                return new PlainLand();
            case 'r':
                return new RockyLand();
            case 't':
                return new RemovableTree();
            case 'T':
                return new ProtectedTree();
            default:
                throw new IllegalArgumentException("Unknown terrain type: " + type);
        }
    }
}