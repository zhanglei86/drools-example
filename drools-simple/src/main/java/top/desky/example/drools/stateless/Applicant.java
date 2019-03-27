package top.desky.example.drools.stateless;

import lombok.Data;

@Data
public class Applicant {

    private String name;
    private int age;

    public Applicant() {
    }

    public Applicant(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
