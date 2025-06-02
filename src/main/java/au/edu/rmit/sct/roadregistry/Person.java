package au.edu.rmit.sct.roadregistry;

import java.util.LocalDate;
import java.util.HashMap;

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private int age;
    private HashMap<Date, Integer> demeritPoints; // offenseDate -> demerit points
    private boolean isSuspended;

    public Person(){
        this.demeritPoints = new HashMap<>();
        this.isSuspended = fasle;
        this.age = 25;
    }

    public boolean addPerson() {
        // Indicate that are implementing this here:
        return true;
    }

    public boolean updatePersonalDetails() {
        // Indicate that you are implementing this here:
        return true;
    }

    public String addDemeritPoints() {
        // Indicate that you are implementing this here:
        return "Success";
    }
}