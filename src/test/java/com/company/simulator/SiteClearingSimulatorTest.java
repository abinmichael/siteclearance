package com.company.simulator;

import com.company.simulator.command.AdvanceCommand;
import com.company.simulator.command.Command;
import com.company.simulator.command.LeftCommand;
import com.company.simulator.command.QuitCommand;
import com.company.simulator.command.RightCommand;
import com.company.simulator.model.Bulldozer;
import com.company.simulator.model.SiteMap;
import com.company.simulator.service.BulldozerService;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SiteClearingSimulatorTest {

    private Bulldozer bulldozer;
    private BulldozerService bulldozerService;
    private char[][] exampleSiteMapChars;
    private SiteMap siteMap;

    @BeforeEach
    void setUp() {
        exampleSiteMapChars = new char[][]{
            {'o', 'o', 't', 'o', 'o', 'o', 'o', 'o', 'o', 'o'},
            {'o', 'o', 'o', 'o', 'o', 'o', 'o', 'T', 'o', 'o'},
            {'r', 'r', 'r', 'o', 'o', 'o', 'o', 'T', 'o', 'o'},
            {'r', 'r', 'r', 'r', 'o', 'o', 'o', 'o', 'o', 'o'},
            {'r', 'r', 'r', 'r', 'r', 't', 'o', 'o', 'o', 'o'}
        };
        siteMap = new SiteMap(exampleSiteMapChars);
        bulldozer = new Bulldozer();
        bulldozerService = new BulldozerService(bulldozer, siteMap);
    }

    @Test
    void testReadSiteMapFromFile(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("sitemap.txt");
        Files.write(file, List.of("ooot", "oToo", "rrTo", "rrrr"));

        Method readSiteMapMethod = SiteClearingSimulator.class.getDeclaredMethod("readSiteMapFromFile", String.class);
        readSiteMapMethod.setAccessible(true); // Make the private method accessible

        char[][] siteMapChars = (char[][]) readSiteMapMethod.invoke(null, file.toString());

        assertNotNull(siteMapChars, "Site map should be read successfully");
        assertEquals(4, siteMapChars.length, "Site map should have 4 rows");
        assertEquals(4, siteMapChars[0].length, "Each row in the site map should have 4 columns");
    }

    @Test
    void testReadSiteMapFromFile_invalidFileFormat(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("invalid_sitemap.txt");
        Files.write(file, List.of("oootoooooo", "oooToo", "rrroooToo"));  // Inconsistent line length

        // Use reflection to access the private method
        Method readSiteMapMethod = SiteClearingSimulator.class.getDeclaredMethod("readSiteMapFromFile", String.class);
        readSiteMapMethod.setAccessible(true); // Make the private method accessible

        char[][] siteMapChars = (char[][]) readSiteMapMethod.invoke(null, file.toString());

        assertNull(siteMapChars, "Site map reading should fail for invalid format");
    }

    @Test
    void testAdvanceCommandExecution() {
        Command advanceCommand = new AdvanceCommand(bulldozerService, 2);
        advanceCommand.execute();
    }

    @Test
    void testLeftCommandExecution() {
        Command leftCommand = new LeftCommand(bulldozerService);
        leftCommand.execute();
    }

    @Test
    void testRightCommandExecution() {
        Command rightCommand = new RightCommand(bulldozerService);
        rightCommand.execute();
    }

    @Test
    void testQuitCommandExecution() {
        Command quitCommand = new QuitCommand(bulldozerService);
        quitCommand.execute();
        assertFalse(bulldozerService.isActive(), "Simulator should be inactive after quit command");
    }

    @Test
    void testMultipleCommandExecution() {
        List<Command> commands = new ArrayList<>();
        commands.add(new AdvanceCommand(bulldozerService, 3));
        commands.add(new LeftCommand(bulldozerService));
        commands.add(new RightCommand(bulldozerService));
        commands.add(new QuitCommand(bulldozerService));

        commands.forEach(Command::execute);

        assertFalse(bulldozerService.isActive(), "Simulator should be inactive after executing quit command");
    }
}
