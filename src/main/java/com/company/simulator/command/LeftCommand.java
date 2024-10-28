package com.company.simulator.command;

import com.company.simulator.service.BulldozerService;

public class LeftCommand implements Command {
    private BulldozerService bulldozerService;

    public LeftCommand(BulldozerService bulldozerService) {
        this.bulldozerService = bulldozerService;
    }

    @Override
    public void execute() {
        bulldozerService.turnLeft();
    }
}
