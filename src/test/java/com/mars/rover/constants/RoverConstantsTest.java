package com.mars.rover.constants;

import com.mars.rover.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoverConstantsTest extends BaseTest {

    @Test
    public void testCommands(){
        Assertions.assertTrue(RoverConstants.validCommands.contains("N"));
        Assertions.assertTrue(RoverConstants.validCommands.contains("S"));
        Assertions.assertTrue(RoverConstants.validCommands.contains("E"));
        Assertions.assertTrue(RoverConstants.validCommands.contains("W"));
        Assertions.assertTrue(RoverConstants.validCommands.contains("F"));
        Assertions.assertTrue(RoverConstants.validCommands.contains("B"));
        Assertions.assertTrue(RoverConstants.validCommands.contains("R"));
        Assertions.assertTrue(RoverConstants.validCommands.contains("L"));
        Assertions.assertFalse(RoverConstants.validCommands.contains("U"));
        Assertions.assertFalse(RoverConstants.validCommands.contains("M"));
    }

}