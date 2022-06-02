package com.mars.rover;

import com.mars.rover.entity.Rover;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoverResponse {
    boolean collisionDetected;
    List<Rover> rovers;
}
