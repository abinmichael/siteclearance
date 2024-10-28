package com.company.simulator.command;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.company.simulator.service.BulldozerService;

public class AdvanceCommandTest {

    private BulldozerService bulldozerService;
    private AdvanceCommand advanceCommand;

    @BeforeEach
    public void setUp() {
        bulldozerService = mock(BulldozerService.class);  // Use Mockito to mock the BulldozerService
        advanceCommand = new AdvanceCommand(bulldozerService,1);
    }

    @Test
    public void testExecuteAdvanceCommand() {
        advanceCommand.execute();
        verify(bulldozerService, times(1)).advance(1);  // Verify that the advance method was called once
    }
}
