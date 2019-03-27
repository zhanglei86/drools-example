package top.desky.example.drools.xls;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Ticket {
    private String destination;
    private String cabin;
    private double duration;
    private boolean food;
    private boolean movie;

    public Ticket() {
    }

    public Ticket(String destination) {
        this.destination = destination;
    }

    public Ticket(String destination, String cabin) {
        this.destination = destination;
        this.cabin = cabin;
    }

    public Ticket(String destination, String cabin, double duration) {
        this.destination = destination;
        this.cabin = cabin;
        this.duration = duration;
    }

}
