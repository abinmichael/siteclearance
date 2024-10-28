package com.company.simulator.command;

import com.company.simulator.service.BulldozerService;

public class QuitCommand implements Command {

    private BulldozerService bulldozerService;

    public QuitCommand(BulldozerService bulldozerService) {
        this.bulldozerService = bulldozerService;
    }

    @Override
    public void execute() {
        bulldozerService.quit();
    }
}