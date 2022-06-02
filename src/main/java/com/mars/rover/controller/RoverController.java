package com.mars.rover.controller;

import com.mars.rover.RoverResponse;
import com.mars.rover.entity.Rover;
import com.mars.rover.request.RoverRequest;
import com.mars.rover.service.RoverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RoverController {

    Logger logger = LoggerFactory.getLogger(RoverController.class);
    @Autowired
    private RoverService roverService;

    @PostMapping("/rover")
    public RoverResponse moveRover(@RequestBody RoverRequest roverRequest,
                                   @RequestParam(defaultValue = "true") boolean startFresh) {
        try {
            logger.info("Rover Request - " + roverRequest.toString());
            return roverService.moveRover(roverRequest, startFresh);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
