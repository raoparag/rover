package com.mars.rover.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rover {
    private String id;
    private String name;
    private Position position;

    public Position getNewPosition(String command) {
        switch (command.toUpperCase()) {
            case "R":
            case "L":
                return updateFacing(command);
            case "F":
            case "B":
                return updateCoordinates(command);
            default:
                return this.position;
        }
    }

    private Position updateFacing(String command) {
        Position newPosition = new Position(this.position);
        switch (this.position.getFacing()) {
            case 'E':
                if (command.equalsIgnoreCase("r")) newPosition.setFacing('S');
                else newPosition.setFacing('N');
                break;
            case 'W':
                if (command.equalsIgnoreCase("r")) newPosition.setFacing('N');
                else newPosition.setFacing('S');
                break;
            case 'N':
                if (command.equalsIgnoreCase("r")) newPosition.setFacing('E');
                else newPosition.setFacing('W');
                break;
            case 'S':
                if (command.equalsIgnoreCase("r")) newPosition.setFacing('W');
                else newPosition.setFacing('E');
                break;
        }
        return newPosition;
    }

    private Position updateCoordinates(String cmd) {
        Position newPosition = new Position(this.position);
        switch (this.position.getFacing()) {
            case 'E':
                if (cmd.equalsIgnoreCase("f")) newPosition.setX(newPosition.getX() + 1);
                else newPosition.setX(newPosition.getX() - 1);
                break;
            case 'W':
                if (cmd.equalsIgnoreCase("f")) newPosition.setX(newPosition.getX() - 1);
                else newPosition.setX(newPosition.getX() + 1);
                break;
            case 'N':
                if (cmd.equalsIgnoreCase("f")) newPosition.setY(newPosition.getY() + 1);
                else newPosition.setY(newPosition.getY() - 1);
                break;
            case 'S':
                if (cmd.equalsIgnoreCase("f")) newPosition.setY(newPosition.getY() - 1);
                else newPosition.setY(newPosition.getY() + 1);
                break;
        }
        return newPosition;
    }
}
