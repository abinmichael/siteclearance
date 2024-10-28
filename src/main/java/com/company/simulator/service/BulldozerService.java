package com.company.simulator.service;

import com.company.simulator.model.Bulldozer;
import com.company.simulator.util.Direction;
import com.company.simulator.model.SiteMap;
import com.company.simulator.terrain.ProtectedTree;
import com.company.simulator.terrain.RemovableTree;
import com.company.simulator.terrain.Terrain;

import static com.company.simulator.util.Constants.*;

public class BulldozerService {
    private Bulldozer bulldozer;
    private SiteMap siteMap;
    private boolean isActive;  // Flag to check if simulation is active

    // Cost tracking variables
    private int communicationOverhead = 0;
    private int fuelUsage = 0;
    private int unclearedSquares = 0;
    private int paintDamageCount = 0;
    private int protectedTreeDamageCount = 0;

    public BulldozerService(Bulldozer bulldozer, SiteMap siteMap) {
        this.bulldozer = bulldozer;
        this.siteMap = siteMap;
        this.isActive = true;
    }

    public int getFuelUsage() {
        return fuelUsage;
    }
    public int getPaintDamageCount() {
        return paintDamageCount;
    }

    public int getProtectedTreeDamageCount() {
        return protectedTreeDamageCount;
    }

    public void advance(int steps) {
        communicationOverhead += COMMUNICATION_COST_PER_COMMAND;

        for (int i = 0; i < steps; i++) {
            int nextHorizontalPosition = bulldozer.getHorizontalPosition();
            int nextVerticalPosition = bulldozer.getVerticalPosition();

            switch (bulldozer.getFacing()) {
                case NORTH:
                    nextVerticalPosition -= 1;
                    break;
                case EAST:
                    nextHorizontalPosition += 1;
                    break;
                case SOUTH:
                    nextVerticalPosition += 1;
                    break;
                case WEST:
                    nextHorizontalPosition -= 1;
                    break;
            }

            // Check if next position is within the site
            if (!siteMap.isWithinBounds(nextHorizontalPosition, nextVerticalPosition)) {
                System.out.println("Bulldozer has moved outside the site. Simulation ends.");
                quit();
                return;
            }

            Terrain terrain = siteMap.getTerrain(nextHorizontalPosition, nextVerticalPosition);

            // Check for protected tree
            if (terrain instanceof ProtectedTree) {
                System.out.println("Attempted to clear a protected tree! Simulation ends.");
                protectedTreeDamageCount += 1;
                quit();
                return;
            }

            // Calculate fuel usage
            fuelUsage += terrain.getFuelCost();

            // Check for paint damage
            if (terrain instanceof RemovableTree && i < steps - 1) {
                // Passing through a removable tree without stopping
                paintDamageCount += 1;
            }

            // Mark terrain as visited
            if (!terrain.isVisited()) {
                terrain.setVisited(true);
                siteMap.decreaseUnclearedSquares();
            }

            // Update bulldozer's position
            bulldozer.setHorizontalPosition(nextHorizontalPosition);
            bulldozer.setVerticalPosition(nextVerticalPosition);
        }
    }

    public void turnLeft() {
        communicationOverhead += COMMUNICATION_COST_PER_COMMAND;
        Direction newDirection = null;
        switch (bulldozer.getFacing()) {
            case NORTH:
                newDirection = Direction.WEST;
                break;
            case EAST:
                newDirection = Direction.NORTH;
                break;
            case SOUTH:
                newDirection = Direction.EAST;
                break;
            case WEST:
                newDirection = Direction.SOUTH;
                break;
        }
        bulldozer.setFacing(newDirection);
    }

    public void turnRight() {
        communicationOverhead += COMMUNICATION_COST_PER_COMMAND;
        Direction newDirection = null;
        switch (bulldozer.getFacing()) {
            case NORTH:
                newDirection = Direction.EAST;
                break;
            case EAST:
                newDirection = Direction.SOUTH;
                break;
            case SOUTH:
                newDirection = Direction.WEST;
                break;
            case WEST:
                newDirection = Direction.NORTH;
                break;
        }
        bulldozer.setFacing(newDirection);
    }

    public void quit() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void printReport() {
        // Calculate uncleared squares
        unclearedSquares = siteMap.getUnclearedSquaresCount();

        // Costs
        int communicationCost = communicationOverhead * COMMUNICATION_COST_PER_COMMAND;
        int paintDamageCost = paintDamageCount * PAINT_DAMAGE_PENALTY_PER_INSTANCE;
        int protectedTreeDamageCost = protectedTreeDamageCount * PROTECTED_TREE_PENALTY;
        int unclearedSquaresCost = unclearedSquares * UNCLEARED_SQUARE_PENALTY_PER_SQUARE;
        int totalCost = communicationCost + fuelUsage + paintDamageCost + protectedTreeDamageCost + unclearedSquaresCost;

        // Output in the required format
        System.out.println("\nThe costs for this land clearing operation were:");
        System.out.println(String.format("%-35s %-10s %-10s", "Item", "Quantity", "Cost"));
        System.out.println(String.format("%-35s %-10d %-10d", "Communication Overhead", communicationOverhead, communicationCost));
        System.out.println(String.format("%-35s %-10d %-10d", "Fuel Usage", fuelUsage, fuelUsage));
        System.out.println(String.format("%-35s %-10d %-10d", "Uncleared Squares", unclearedSquares, unclearedSquaresCost));
        System.out.println(String.format("%-35s %-10d %-10d", "Destruction of protected tree", protectedTreeDamageCount, protectedTreeDamageCost));
        System.out.println(String.format("%-35s %-10d %-10d", "Paint damage to bulldozer", paintDamageCount, paintDamageCost));
        System.out.println("----");
        System.out.println(String.format("%-45s %-10d", "Total", totalCost));
    }
}
