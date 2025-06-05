package au.edu.rmit.sct.roadregistry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @BeforeEach
    public void cleanUpPersonFile() {
        File file = new File("persons.txt");
        if (file.exists()) {
            boolean deleted = file.delete();
            System.out.println(deleted ? "Cleanup: persons.txt deleted." : "Cleanup: Failed to delete persons.txt.");
        }
    }

    // Test for successful update with valid inputs.
    @Test
    public void testValidUpdate_AllFieldsValid_ShouldSucceed() throws IOException {
        String original = "56s_d%&fAB,John,Doe,12|Main St|Melbourne|Victoria|Australia,15-11-2000";
        writeToFile(original);

        Person updated = new Person("56s_d%&fAB", "Johnny", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-11-2000");

        Person person = new Person();
        boolean result = person.updatePersonalDetails("56s_d%&fAB", updated);

        assertTrue(result, "Expected update to succeed when all conditions are satisfied.");
    }

    // Test to make sure address update fails if underage.
    @Test
    public void testUnderageChangeAddress_ShouldFail() throws IOException {
        String original = "56s_d%&fAB,John,Doe,45|Old St|Melbourne|Victoria|Australia,15-11-2010";
        writeToFile(original);

        Person updated = new Person("56s_d%&fAB", "John", "Doe", "45|New St|Melbourne|Victoria|Australia",
                "15-11-2010");

        Person person = new Person();
        boolean result = person.updatePersonalDetails("56s_d%&fAB", updated);

        assertFalse(result, "Expected update to fail since person is under 18 and address is changing.");
    }

    // Test to make sure update fails if birthday is being changed and another field
    // (first name) is also being changed.
    @Test
    public void testChangeBirthdayAndName_ShouldFail() throws IOException {
        String original = "56s_d%&fAB,John,Doe,22|Elm St|Melbourne|Victoria|Australia,15-11-1995";
        writeToFile(original);

        Person updated = new Person("56s_d%&fAB", "Jake", "Doe", "22|Elm St|Melbourne|Victoria|Australia",
                "16-11-1995");

        Person person = new Person();
        boolean result = person.updatePersonalDetails("56s_d%&fAB", updated);

        assertFalse(result, "Expected update to fail since birthday and first name are both being changed.");
    }

    // Test to make sure update fails if person's ID starts with even number.
    @Test
    public void testChangeIDStartingWithEvenDigit_ShouldFail() throws IOException {
        String original = "46s_d%&fXY,John,Doe,88|Long Rd|Melbourne|Victoria|Australia,10-12-1992";
        writeToFile(original);

        Person updated = new Person("57x_d%&fXY", "John", "Doe", "88|Long Rd|Melbourne|Victoria|Australia",
                "10-12-1992");

        Person person = new Person();
        boolean result = person.updatePersonalDetails("46s_d%&fXY", updated);

        assertFalse(result, "Expected update to fail since original ID starts with even digit and is being changed.");
    }

    // Test to make sure update succeeds even when no fields are changed.
    @Test
    public void testNoChangeAtAll_ShouldSucceed() throws IOException {
        String original = "56s_d%&fAB,John,Doe,12|Main St|Melbourne|Victoria|Australia,15-11-2000";
        writeToFile(original);

        Person updated = new Person("56s_d%&fAB", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-11-2000");

        Person person = new Person();
        boolean result = person.updatePersonalDetails("56s_d%&fAB", updated);

        assertTrue(result, "Expected update to succeed even if no fields changed.");
    }

    // Test to make sure addPerson() succeeds when all conditions are met.
    @Test 
    public void testAddValidPerson_ShouldSucceed() throws IOException {
        Person person = new Person("56s_d%&fAB", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-11-2000");
        
        boolean result = person.addPerson();
        assertTrue(result, "Expected to succeed when all conditions are satisfied.");
    }

    //  Test to make sure addPerson() fails when personID is less than 10 characters.
    @Test
    public void testPersonIDWithLessThan10Chars_ShouldFail() throws IOException{
        Person person = new Person("56_d%&AB", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-11-2000");
        
        boolean result = person.addPerson();
        assertFalse(result, "Expected to fail when personID is less than 10 characters.");
    }

    //  Test to make sure addPerson() fails when Person address is in the incorrect format.
    @Test
    public void testAddressIncorrectFormat_ShouldFail() throws IOException {
        Person person = new Person("56_d%&AB", "John", "Doe", "12 Main St Melbourne Victoria Australia",
                "15-11-2000");
        
        boolean result = person.addPerson();
        assertFalse(result, "Expected to fail when address is in the incorrect format.");
    }

    private void writeToFile(String content) throws IOException {
        File file = new File("persons.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.newLine();
        }
    }


    // -------------------- Teesha's addDemeritPoints() Test Cases --------------------

@Test
public void testValidDemeritEntry_ShouldReturnSuccess() {
    Person person = new Person();
    person.setPersonID("56s_d%&fAB");
    person.setAge(25);
    String result = person.addDemeritPoints("01-06-2024", 3);
    assertEquals("Success", result, "Expected 'Success' for valid input.");
}

@Test
public void testInvalidDateFormat_ShouldReturnFailed() {
    Person person = new Person();
    person.setPersonID("56s_d%&fAB");
    person.setAge(25);
    String result = person.addDemeritPoints("2024/06/01", 4);
    assertEquals("Failed", result, "Expected 'Failed' due to invalid date format.");
}

@Test
public void testInvalidPointRange_ShouldReturnFailed() {
    Person person = new Person();
    person.setPersonID("56s_d%&fAB");
    person.setAge(25);
    String result = person.addDemeritPoints("05-06-2024", 0);
    assertEquals("Failed", result, "Expected 'Failed' due to point value out of range.");
}

@Test
public void testOffenseDate_ShouldReturnFailed() {
    Person person = new Person();
    person.setPersonID("56s_d%&fAB");
    person.setAge(25);

    String firstResult = person.addDemeritPoints("10-06-2024", 2); // valid entry
    String secondResult = person.addDemeritPoints("10-06-2024", 3); // duplicate entry

    assertEquals("Failed", secondResult, "Expected 'Failed' due to duplicate offense date.");
}


@Test
public void testSuspensionAfterMultipleValidOffenses_ShouldSuspend() {
    Person person = new Person();
    person.setPersonID("56s_d@kFAB");
    person.setAge(25); // Age ≥ 21, threshold is 12

    // Add three offenses within 2 years
    person.addDemeritPoints("01-06-2024", 5);
    person.addDemeritPoints("02-06-2024", 5);
    person.addDemeritPoints("03-06-2024", 4); // total = 14

    assertTrue(person.getIsSuspended(), "Expected suspension when total > 12 for age ≥ 21.");
}

}