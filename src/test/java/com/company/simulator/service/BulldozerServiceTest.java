package com.company.simulator.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.company.simulator.terrain.PlainLand;
import com.company.simulator.terrain.ProtectedTree;
import com.company.simulator.terrain.RemovableTree;
import com.company.simulator.terrain.RockyLand;
import com.company.simulator.terrain.Terrain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.company.simulator.model.Bulldozer;
import com.company.simulator.model.SiteMap;
import com.company.simulator.util.Direction;

public class BulldozerServiceTest {

    private Bulldozer bulldozer;
    private SiteMap siteMap;
    private BulldozerService bulldozerService;

    @BeforeEach
    public void setUp() {
        bulldozer = new Bulldozer();
        siteMap = mock(SiteMap.class);
        bulldozerService = new BulldozerService(bulldozer, siteMap);
    }

    @Test
    public void testAdvance() {
        siteMap.setTerrainMap(new Terrain[1][2]);
        when(siteMap.getCols()).thenReturn(2);
        when(siteMap.getRows()).thenReturn(1);
        when(siteMap.isWithinBounds(anyInt(),anyInt())).thenReturn(true);
        when(siteMap.getTerrain(0, 0)).thenReturn(new PlainLand());
        when(siteMap.getTerrain(1, 0)).thenReturn(new PlainLand());
        bulldozerService.advance(2);
        assertEquals(2, bulldozerService.getFuelUsage());  // Assuming 1 fuel per step
    }

    @Test
    public void testTurnLeft() {
        bulldozerService.turnLeft();
        assertEquals(Direction.NORTH, bulldozer.getFacing());
    }

    @Test
    public void testTurnRight() {
        bulldozerService.turnRight();
        assertEquals(Direction.SOUTH, bulldozer.getFacing());
    }

    @Test
    public void testInteractionWithRemovableTree() {
        siteMap.setTerrainMap(new Terrain[1][2]);
        when(siteMap.getCols()).thenReturn(2);
        when(siteMap.getRows()).thenReturn(1);
        when(siteMap.isWithinBounds(anyInt(),anyInt())).thenReturn(true);
        when(siteMap.getTerrain(0, 0)).thenReturn(new RemovableTree());
        when(siteMap.getTerrain(1, 0)).thenReturn(new RemovableTree());
        bulldozerService.advance(2);
        assertEquals(1,bulldozerService.getPaintDamageCount());
        assertEquals(4, bulldozerService.getFuelUsage());
    }

    @Test
    public void testInteractionWithProtectedTree() {
        siteMap.setTerrainMap(new Terrain[1][2]);
        when(siteMap.getCols()).thenReturn(2);
        when(siteMap.getRows()).thenReturn(1);
        when(siteMap.isWithinBounds(anyInt(),anyInt())).thenReturn(true);
        when(siteMap.getTerrain(0, 0)).thenReturn(new PlainLand());
        when(siteMap.getTerrain(1, 0)).thenReturn(new ProtectedTree());
        bulldozerService.advance(2);
        assertEquals(1, bulldozerService.getProtectedTreeDamageCount());
    }

    @Test
    public void testAdvanceIntoRockyLand() {
        siteMap.setTerrainMap(new Terrain[1][2]);
        when(siteMap.getCols()).thenReturn(2);
        when(siteMap.getRows()).thenReturn(1);
        when(siteMap.isWithinBounds(anyInt(),anyInt())).thenReturn(true);
        when(siteMap.getTerrain(0, 0)).thenReturn(new PlainLand());
        when(siteMap.getTerrain(1, 0)).thenReturn(new RockyLand());
        bulldozerService.advance(2);
        assertEquals(3, bulldozerService.getFuelUsage());
    }

   @Test
    public void testAdvanceOutOfBounds() {
        when(siteMap.isWithinBounds(0, 0)).thenReturn(false);
        bulldozerService.advance(1);
    }
}

