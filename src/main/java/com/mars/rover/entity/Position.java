package com.mars.rover.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {
    private int x;
    private int y;
    private char facing;

    public Position(Position p) {
        this.x = p.getX();
        this.y = p.getY();
        this.facing = p.getFacing();
    }

    public boolean isColliding(Position p) {
        if (this == p) return true;
        if (p == null) return false;

        if (x != p.x) return false;
        return y == p.y;
    }
}
