package top.desky.example.drools.fact;

import lombok.Data;

@Data
public class Person {
    private String name;
    private byte age;

    public Person() {
    }

    public Person(String name, byte age) {
        this.name = name;
        this.age = age;
    }

}
