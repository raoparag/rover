package com.mars.rover.service;

import com.mars.rover.BaseTest;
import com.mars.rover.RoverResponse;
import com.mars.rover.entity.Rover;
import com.mars.rover.exception.RoverException;
import com.mars.rover.repository.RoverRepo;
import com.mars.rover.request.RoverRequest;
import com.mars.rover.request.RoverRequestSingle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoverServiceTest extends BaseTest {

    @Spy
    private RoverRepo roverRepo = new RoverRepo();

    @InjectMocks
    private RoverService roverService;

    @Test
    void moveRoverValidationErrors() {
        RoverException roverException = assertThrows(RoverException.class,
                () -> {
                    roverService.moveRover(
                            getRoverRequest("f,f,r,d,f", 'N'), true);
                });
        Assertions.assertEquals("Invalid commands provided in the input.", roverException.getMessage());
        roverException = Assertions.assertThrows(RoverException.class,
                () -> {roverService.moveRover(
                        getRoverRequest(",f,f,r,r,f", 'N'), true);});
        Assertions.assertEquals("Invalid commands provided in the input.", roverException.getMessage());
        roverException = Assertions.assertThrows(RoverException.class,
                () -> {roverService.moveRover(
                        getRoverRequest("f,f,r,,f", 'N'), true);});
        Assertions.assertEquals("Invalid commands provided in the input.", roverException.getMessage());
        roverException = Assertions.assertThrows(RoverException.class,
                () -> {roverService.moveRover(
                        getRoverRequest("f,f,r,fr", 'N'), true);});
        Assertions.assertEquals("Invalid commands provided in the input.", roverException.getMessage());
        roverException = Assertions.assertThrows(RoverException.class,
                () -> {roverService.moveRover(
                        getRoverRequest("f,f,r,d,f", 'F'), true);});
        Assertions.assertEquals("Invalid commands provided in the input.", roverException.getMessage());
        roverException = Assertions.assertThrows(RoverException.class,
                () -> {roverService.moveRover(
                        getCollidingRoverRequestInput(), true);});
        Assertions.assertEquals("Collision detected. Request Aborted", roverException.getMessage());
    }

    private RoverRequest getRoverRequest(String command, char facing) {
        RoverRequest roverRequest = new RoverRequest();
        List<RoverRequestSingle> rovers = new ArrayList<>();
        RoverRequestSingle roverRequestSingle = new RoverRequestSingle();
        roverRequestSingle.setName("rover1");
        roverRequestSingle.setX(3);
        roverRequestSingle.setY(4);
        roverRequestSingle.setFacing(facing);
        roverRequestSingle.setCommand(command);
        rovers.add(roverRequestSingle);
        roverRequest.setRovers(rovers);
        return roverRequest;
    }

    private RoverRequest getCollidingRoverRequestInput() {
        RoverRequest roverRequest = new RoverRequest();
        List<RoverRequestSingle> rovers = new ArrayList<>();
        RoverRequestSingle roverRequestSingle = new RoverRequestSingle();
        roverRequestSingle.setName("rover1");
        roverRequestSingle.setX(3);
        roverRequestSingle.setY(4);
        roverRequestSingle.setFacing('N');
        roverRequestSingle.setCommand("f,f,r,f,f");
        rovers.add(roverRequestSingle);
        RoverRequestSingle roverRequestSingle2 = new RoverRequestSingle();
        roverRequestSingle2.setName("rover2");
        roverRequestSingle2.setX(3);
        roverRequestSingle2.setY(4);
        roverRequestSingle2.setFacing('E');
        roverRequestSingle2.setCommand("f,f,r,f,f");
        rovers.add(roverRequestSingle2);
        roverRequest.setRovers(rovers);
        return roverRequest;
    }

    private RoverRequest getNonCollidingRoverRequestCommands() {
        RoverRequest roverRequest = new RoverRequest();
        List<RoverRequestSingle> rovers = new ArrayList<>();
        RoverRequestSingle roverRequestSingle = new RoverRequestSingle();
        roverRequestSingle.setName("rover1");
        roverRequestSingle.setX(3);
        roverRequestSingle.setY(4);
        roverRequestSingle.setFacing('N');
        roverRequestSingle.setCommand("f,f,r,f,f");
        rovers.add(roverRequestSingle);
        RoverRequestSingle roverRequestSingle2 = new RoverRequestSingle();
        roverRequestSingle2.setName("rover2");
        roverRequestSingle2.setX(1);
        roverRequestSingle2.setY(2);
        roverRequestSingle2.setFacing('N');
        roverRequestSingle2.setCommand("f,f,r,f,f");
        rovers.add(roverRequestSingle2);
        roverRequest.setRovers(rovers);
        return roverRequest;
    }

    private RoverRequest getCollidingRoverRequestCommands() {
        RoverRequest roverRequest = new RoverRequest();
        List<RoverRequestSingle> rovers = new ArrayList<>();
        RoverRequestSingle roverRequestSingle = new RoverRequestSingle();
        roverRequestSingle.setName("rover1");
        roverRequestSingle.setX(3);
        roverRequestSingle.setY(4);
        roverRequestSingle.setFacing('N');
        roverRequestSingle.setCommand("f,f,r,f,f");
        rovers.add(roverRequestSingle);
        RoverRequestSingle roverRequestSingle2 = new RoverRequestSingle();
        roverRequestSingle2.setName("rover2");
        roverRequestSingle2.setX(1);
        roverRequestSingle2.setY(2);
        roverRequestSingle2.setFacing('N');
        roverRequestSingle2.setCommand("f,f,r,f,f,l,f,f,r,f,f");
        rovers.add(roverRequestSingle2);
        roverRequest.setRovers(rovers);
        return roverRequest;
    }

    private RoverRequest getRoverRequest() {
        return getRoverRequest("f,f,r,f,f", 'N');
    }

    @Test
    public void moveRoverSuccessSingle() {
        RoverRequest roverRequest = getRoverRequest();
        RoverResponse roverResponse = roverService.moveRover(roverRequest, true);
        Assertions.assertEquals(1, roverResponse.getRovers().size());
        Assertions.assertFalse(roverResponse.isCollisionDetected());
        Rover rover = roverResponse.getRovers().get(0);
        Assertions.assertEquals(5, rover.getPosition().getX());
        Assertions.assertEquals(6, rover.getPosition().getY());
        Assertions.assertEquals('E', rover.getPosition().getFacing());
    }

    @Test
    public void moveRoverSuccessMultiple() {
        RoverRequest roverRequest = getNonCollidingRoverRequestCommands();
        RoverResponse roverResponse = roverService.moveRover(roverRequest, true);
        List<Rover> rovers = roverResponse.getRovers();
        rovers.sort(Comparator.comparing(Rover::getName));
        Assertions.assertEquals(2, rovers.size());
        Assertions.assertFalse(roverResponse.isCollisionDetected());
        Rover rover = rovers.get(0);
        Assertions.assertEquals(5, rover.getPosition().getX());
        Assertions.assertEquals(6, rover.getPosition().getY());
        Assertions.assertEquals('E', rover.getPosition().getFacing());
        rover = rovers.get(1);
        Assertions.assertEquals(3, rover.getPosition().getX());
        Assertions.assertEquals(4, rover.getPosition().getY());
        Assertions.assertEquals('E', rover.getPosition().getFacing());
    }

    @Test
    public void moveRoverSuccessMultipleColliding() {
        RoverRequest roverRequest = getCollidingRoverRequestCommands();
        RoverResponse roverResponse = roverService.moveRover(roverRequest, true);
        List<Rover> rovers = roverResponse.getRovers();
        rovers.sort(Comparator.comparing(Rover::getName));
        Assertions.assertEquals(2, rovers.size());
        Assertions.assertTrue(roverResponse.isCollisionDetected());
        Rover rover = rovers.get(0);
        Assertions.assertEquals(5, rover.getPosition().getX());
        Assertions.assertEquals(6, rover.getPosition().getY());
        Assertions.assertEquals('E', rover.getPosition().getFacing());
        rover = rovers.get(1);
        Assertions.assertEquals(4, rover.getPosition().getX());
        Assertions.assertEquals(6, rover.getPosition().getY());
        Assertions.assertEquals('E', rover.getPosition().getFacing());
    }
}