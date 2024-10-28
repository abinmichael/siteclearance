package com.company.simulator.terrain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class TerrainFactoryTest {

    @Test
    void testCreatePlainLand() {
        Terrain terrain = TerrainFactory.createTerrain('o');
        assertTrue(terrain instanceof PlainLand, "Factory should create PlainLand for 'o'");
    }

    @Test
    void testCreateRockyLand() {
        Terrain terrain = TerrainFactory.createTerrain('r');
        assertTrue(terrain instanceof RockyLand, "Factory should create RockyLand for 'r'");
    }

    @Test
    void testCreateRemovableTree() {
        Terrain terrain = TerrainFactory.createTerrain('t');
        assertTrue(terrain instanceof RemovableTree, "Factory should create RemovableTree for 't'");
    }

    @Test
    void testCreateProtectedTree() {
        Terrain terrain = TerrainFactory.createTerrain('T');
        assertTrue(terrain instanceof ProtectedTree, "Factory should create ProtectedTree for 'T'");
    }

    @Test
    void testUnknownTerrainType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TerrainFactory.createTerrain('x');
        });
        assertEquals("Unknown terrain type: x", exception.getMessage());
    }
}
