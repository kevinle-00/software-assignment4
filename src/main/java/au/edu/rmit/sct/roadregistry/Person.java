package au.edu.rmit.sct.roadregistry;

public class Person {
    private String personID;
    private String firstName;
    private String lastName;

    public Person(String personID, String firstName, String lastName) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}