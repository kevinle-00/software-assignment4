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
        this.isSuspended = false;
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

    public String addDemeritPoints(String dateOfOffense, int points) {
        // Condition 1: Check DD-MM-YYYY format
        // Condition 2: Check points range
        //Condition 3: Check Offenses within the last 2 years 

        return "Success";
    }
}