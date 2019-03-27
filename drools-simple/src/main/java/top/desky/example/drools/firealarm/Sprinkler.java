package top.desky.example.drools.firealarm;

import lombok.Data;

@Data
public class Sprinkler {

    private Room room;
    private boolean on;

    public Sprinkler() {
    }

    public Sprinkler(Room room) {
        this.room = room;
    }

}
