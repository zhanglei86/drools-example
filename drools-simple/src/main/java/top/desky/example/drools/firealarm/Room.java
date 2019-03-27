package top.desky.example.drools.firealarm;

import lombok.Data;

@Data
public class Room {
    private String name;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

}
