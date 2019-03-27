package top.desky.example.drools.stateless;

import lombok.Data;

import java.util.Date;

@Data
public class Application {
    private Date dateApplied;
    private boolean valid = true;
}
