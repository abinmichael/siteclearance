package com.company.simulator.command;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.company.simulator.command.LeftCommand;
import com.company.simulator.service.BulldozerService;

public class LeftCommandTest {

    private BulldozerService bulldozerService;
    private LeftCommand leftCommand;

    @BeforeEach
    public void setUp() {
        bulldozerService = mock(BulldozerService.class);
        leftCommand = new LeftCommand(bulldozerService);
    }

    @Test
    public void testExecuteLeftCommand() {
        leftCommand.execute();
        verify(bulldozerService, times(1)).turnLeft();  // Verify that the turnLeft method was called once
    }
}
