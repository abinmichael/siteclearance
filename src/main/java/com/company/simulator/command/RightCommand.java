package com.company.simulator.command;

import com.company.simulator.service.BulldozerService;

public class RightCommand implements Command {
    private BulldozerService bulldozerService;

    public RightCommand(BulldozerService bulldozerService) {
        this.bulldozerService = bulldozerService;
    }

    @Override
    public void execute() {
        bulldozerService.turnRight();
    }
}
