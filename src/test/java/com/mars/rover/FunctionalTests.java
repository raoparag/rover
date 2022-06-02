package com.mars.rover;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mars.rover.entity.Rover;
import com.mars.rover.request.RoverRequest;
import com.mars.rover.request.RoverRequestSingle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FunctionalTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void singleRoverMove() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/rover?startFresh=true")
                        .content(getRoverRequest())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        RoverResponse roverResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), RoverResponse.class);
        Assertions.assertNotNull(roverResponse);
        Assertions.assertFalse(roverResponse.isCollisionDetected());
        List<Rover> rovers = roverResponse.getRovers();
        Assertions.assertEquals(1, rovers.size());
        Rover rover = roverResponse.getRovers().get(0);
        Assertions.assertEquals(5, rover.getPosition().getX());
        Assertions.assertEquals(6, rover.getPosition().getY());
        Assertions.assertEquals('E', rover.getPosition().getFacing());
    }

    @Test
    public void multipleRoverMove() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/rover?startFresh=true")
                        .content(getNonCollidingRoverRequestCommands())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        RoverResponse roverResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), RoverResponse.class);
        Assertions.assertNotNull(roverResponse);
        List<Rover> rovers = roverResponse.getRovers();
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
    public void multipleRoverMoveCollision() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/rover?startFresh=true")
                        .content(getCollidingRoverRequestCommands())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        ObjectMapper mapper = new ObjectMapper();
        RoverResponse roverResponse = mapper.readValue(mvcResult.getResponse().getContentAsString(), RoverResponse.class);
        Assertions.assertNotNull(roverResponse);
        List<Rover> rovers = roverResponse.getRovers();
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

    private String getRoverRequest() throws JsonProcessingException {
        RoverRequest roverRequest = new RoverRequest();
        List<RoverRequestSingle> rovers = new ArrayList<>();
        RoverRequestSingle roverRequestSingle = new RoverRequestSingle();
        roverRequestSingle.setName("rover1");
        roverRequestSingle.setX(3);
        roverRequestSingle.setY(4);
        roverRequestSingle.setFacing('N');
        roverRequestSingle.setCommand("f,f,r,f,f");
        rovers.add(roverRequestSingle);
        roverRequest.setRovers(rovers);
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(roverRequest);
        return requestJson;
    }

    private String getNonCollidingRoverRequestCommands() throws JsonProcessingException {
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
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(roverRequest);
        return requestJson;
    }

    private String getCollidingRoverRequestCommands() throws JsonProcessingException {
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
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(roverRequest);
        return requestJson;
    }

}
