package com.company.simulator.model;

import com.company.simulator.util.Direction;

public class Bulldozer {
    private int horizontalPosition;
    private int verticalPosition;
    private Direction facing;

    public Bulldozer() {
        this.horizontalPosition = -1; // Starts outside the site (to the west of the first row)
        this.verticalPosition = 0;
        this.facing = Direction.EAST; // Facing east initially
    }

    // Getters and Setters
    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public void setHorizontalPosition(int horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public void setVerticalPosition(int verticalPosition) {
        this.verticalPosition = verticalPosition;
    }

    public Direction getFacing() {
        return facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }
}
