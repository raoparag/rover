package com.mars.rover.controller;

import com.mars.rover.BaseTest;
import com.mars.rover.RoverResponse;
import com.mars.rover.exception.RoverException;
import com.mars.rover.request.RoverRequest;
import com.mars.rover.service.RoverService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

class RoverControllerTest extends BaseTest {

    @Mock
    private RoverService roverService;

    @InjectMocks
    private RoverController roverController;

    @Test
    void moveRoverSuccess() {
        Mockito.when(roverService.moveRover(Mockito.any(), Mockito.anyBoolean())).thenReturn(new RoverResponse());
        roverController.moveRover(new RoverRequest(), true);
        Mockito.verify(roverService).moveRover(Mockito.any(), Mockito.anyBoolean());
    }

    @Test
    void moveRoverException() {
        Mockito.when(roverService.moveRover(Mockito.any(), Mockito.anyBoolean())).thenThrow(new RoverException("test"));
        Assertions.assertThrows(ResponseStatusException.class,
                () -> {roverController.moveRover(new RoverRequest(), true);});
    }
}