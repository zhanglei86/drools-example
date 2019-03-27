package top.desky.example.drools.firealarm;

import lombok.Data;

@Data
public class Fire {
    private Room room;

    public Fire() {
    }

    public Fire(Room room) {
        this.room = room;
    }

}
