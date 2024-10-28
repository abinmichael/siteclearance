package com.company.simulator.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.company.simulator.service.BulldozerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RightCommandTest {
    private BulldozerService bulldozerService;
    private RightCommand rightCommand;

    @BeforeEach
    public void setUp() {
        bulldozerService = mock(BulldozerService.class);
        rightCommand = new RightCommand(bulldozerService);
    }

    @Test
    public void testExecuteRightCommand() {
        rightCommand.execute();
        verify(bulldozerService, times(1)).turnRight();
    }
}
