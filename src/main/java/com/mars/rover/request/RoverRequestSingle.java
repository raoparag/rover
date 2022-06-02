package com.mars.rover.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoverRequestSingle {
    private String name;
    private int x;
    private int y;
    private char facing;
    private String command;
}
