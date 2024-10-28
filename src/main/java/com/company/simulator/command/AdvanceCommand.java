package com.company.simulator.command;

import com.company.simulator.service.BulldozerService;

public class AdvanceCommand implements Command {
    private BulldozerService bulldozerService;
    private int steps;

    public AdvanceCommand(BulldozerService bulldozerService, int steps) {
        this.bulldozerService = bulldozerService;
        this.steps = steps;
    }

    @Override
    public void execute() {
        bulldozerService.advance(steps);
    }
}