package com.company.simulator.model;

import com.company.simulator.terrain.PlainLand;
import com.company.simulator.terrain.ProtectedTree;
import com.company.simulator.terrain.RemovableTree;
import com.company.simulator.terrain.RockyLand;
import com.company.simulator.terrain.Terrain;
import com.company.simulator.terrain.TerrainFactory;

public class SiteMap {
    private Terrain[][] terrainMap;
    private int rows;
    private int cols;
    private int unclearedSquaresCount;

    public SiteMap(char[][] siteMapChars) {
        rows = siteMapChars.length;
        cols = siteMapChars[0].length;
        terrainMap = new Terrain[rows][cols];

        int totalUncleared = 0;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                char terrainChar = siteMapChars[y][x];
                Terrain terrain = TerrainFactory.createTerrain(terrainChar);
                terrainMap[y][x] = terrain;

                // Count uncleared squares (exclude protected trees)
                if (!(terrain instanceof ProtectedTree)) {
                    totalUncleared++;
                }
            }
        }

        unclearedSquaresCount = totalUncleared;
    }

    public Terrain[][] getTerrainMap() {
        return terrainMap;
    }

    public void setTerrainMap(Terrain[][] terrainMap) {
        this.terrainMap = terrainMap;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setUnclearedSquaresCount(int unclearedSquaresCount) {
        this.unclearedSquaresCount = unclearedSquaresCount;
    }

    public boolean isWithinBounds(int x, int y) {
        return y >= 0 && y < rows && x >= 0 && x < cols;
    }

    public Terrain getTerrain(int x, int y) {
        return terrainMap[y][x];
    }

    public void decreaseUnclearedSquares() {
        unclearedSquaresCount--;
    }

    public int getUnclearedSquaresCount() {
        return unclearedSquaresCount;
    }

    public void displayMap() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Terrain terrain = terrainMap[y][x];
                if (terrain instanceof PlainLand) {
                    System.out.print("o ");
                } else if (terrain instanceof RockyLand) {
                    System.out.print("r ");
                } else if (terrain instanceof RemovableTree) {
                    System.out.print("t ");
                } else if (terrain instanceof ProtectedTree) {
                    System.out.print("T ");
                }
            }
            System.out.println();
        }
    }
}
