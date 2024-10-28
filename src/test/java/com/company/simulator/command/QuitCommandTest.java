package com.company.simulator.command;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.company.simulator.service.BulldozerService;

public class QuitCommandTest {

    private BulldozerService bulldozerService;
    private QuitCommand quitCommand;

    @BeforeEach
    public void setUp() {
        bulldozerService = mock(BulldozerService.class);
        quitCommand = new QuitCommand(bulldozerService);
    }

    @Test
    public void testExecuteQuitCommand() {
        quitCommand.execute();
        verify(bulldozerService, times(1)).quit();  // Verify that the quit method was called
    }
}
