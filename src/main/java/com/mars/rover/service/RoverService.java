package com.mars.rover.service;

import com.mars.rover.RoverResponse;
import com.mars.rover.entity.Position;
import com.mars.rover.entity.Rover;
import com.mars.rover.exception.RoverCollisionException;
import com.mars.rover.exception.RoverException;
import com.mars.rover.repository.RoverRepo;
import com.mars.rover.request.RoverRequest;
import com.mars.rover.request.RoverRequestSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RoverService {

    @Autowired
    private RoverRepo roverRepo;

    private static final Set<String> validCommands = new HashSet<>();

    @PostConstruct
    public void init() {
        validCommands.add("N");
        validCommands.add("S");
        validCommands.add("E");
        validCommands.add("W");
        validCommands.add("F");
        validCommands.add("B");
        validCommands.add("R");
        validCommands.add("L");
    }

    public RoverResponse moveRover(RoverRequest roverRequest, boolean startFresh) {
        validateRequest(roverRequest);
        if (startFresh) roverRepo.init();
        Map<String, String[]> roverCommands = addRoversToRepo(roverRequest);
        boolean collisionDetected = executeCommands(roverCommands);
        RoverResponse roverResponse = new RoverResponse();
        roverResponse.setRovers(roverRepo.getAll());
        roverResponse.setCollisionDetected(collisionDetected);
        return roverResponse;
    }

    private boolean executeCommands(Map<String, String[]> roverCommands) {
        int maxLengthOfCommands = 0;
        for (String[] commands: roverCommands.values()) {
            maxLengthOfCommands = Math.max(maxLengthOfCommands, commands.length);
        }
        try {
            int i = 0;
            while (i < maxLengthOfCommands) {
                for (String roverId : roverCommands.keySet()) {
                    if (i < roverCommands.get(roverId).length) {
                        Position p = getNewPosition(roverId, roverCommands.get(roverId)[i]);
                        checkForCollision(p);
                        roverRepo.get(roverId).get().setPosition(p);
                    }
                }
                i++;
            }
        } catch (RoverCollisionException rce) {
            return true;
        }
        return false;
    }

    private void checkForCollision(Position p) {
        for (Rover rover: roverRepo.getAll()) {
            if (rover.getPosition().equals(p))
                throw new RoverCollisionException("Collision occurred. Rest of the commands aborted.");
        }
    }

    private Position getNewPosition(String roverId, String command) {
        if (!roverRepo.get(roverId).isPresent())
            throw new RoverException("Internal Error. Rover not present in repo");
        return roverRepo.get(roverId).get().getNewPosition(command);
    }

    private Map<String, String[]> addRoversToRepo(RoverRequest roverRequest) {
        Map<String, String[]> roverCommands = new HashMap<>();
        for (RoverRequestSingle request:roverRequest.getRovers()) {
            Rover rover = new Rover();
            rover.setId(UUID.randomUUID().toString());
            rover.setName(request.getName());
            rover.setPosition(new Position(request.getX(), request.getY(), request.getFacing()));
            roverCommands.put(rover.getId(), request.getCommand().toUpperCase().split(","));
            roverRepo.add(rover);
        }
        return roverCommands;
    }

    private void validateRequest(RoverRequest roverRequest) {
        checkForInvalidInput(roverRequest);
        checkForCollision(roverRequest);
    }

    private void checkForCollision(RoverRequest roverRequest) {
        Set<String> positions = new HashSet<>();
        for (RoverRequestSingle request:roverRequest.getRovers()) {
            String newPosition = request.getX() + "#" + request.getY() + "#" + request.getFacing();
            newPosition = newPosition.toUpperCase();
            if (positions.contains(newPosition)) {
                throw new RoverException("Collision detected. Request Aborted");
            }
            positions.add(newPosition);
        }
    }

    private void checkForInvalidInput(RoverRequest roverRequest) {
        for (RoverRequestSingle request:roverRequest.getRovers()) {
            String[] commands = request.getCommand().split(",");
            for (String command: commands) {
                if (!validCommands.contains(command.toUpperCase()))
                    throw new RuntimeException("Invalid commands provided in the input.");
            }
        }
    }
}
