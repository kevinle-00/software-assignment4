package au.edu.rmit.sct.roadregistry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class PersonTest {

    @Test
    @Order(1)
    public void sampleTest() {
        assertTrue(true);
    }

    // File cleanup for before conducting each test
    @BeforeEach
    public void cleanUpPersonFile() {
        File file = new File("persons.txt");
        if (file.exists()) {
            boolean deleted = file.delete();
            System.out.println(deleted ? "Cleanup: persons.txt deleted." : "Cleanup: Failed to delete persons.txt.");
        }
    }

    // -------------------- Kevin's updatePersonalDetails() Test Cases
    // --------------------

    // Test for successful update with valid inputs.
    @Test
    @Order(16)
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
    @Order(12)
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
    @Order(13)
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
    @Order(14)
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
    @Order(15)
    public void testNoChangeAtAll_ShouldSucceed() throws IOException {
        String original = "56s_d%&fAB,John,Doe,12|Main St|Melbourne|Victoria|Australia,15-11-2000";
        writeToFile(original);

        Person updated = new Person("56s_d%&fAB", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-11-2000");

        Person person = new Person();
        boolean result = person.updatePersonalDetails("56s_d%&fAB", updated);

        assertTrue(result, "Expected update to succeed even if no fields changed.");
    }

    // -------------------- Jack's addPerson() Test Cases --------------------

    // Test to make sure addPerson() succeeds when all Person's information
    // conditions are met.
    @Test
    @Order(2)
    public void testAddValidPerson_ShouldSucceed() throws IOException {
        Person person = new Person("56s_d%&fAB", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-11-2000");

        boolean result = person.addPerson();
        assertTrue(result, "Expected to succeed when all Person information conditions are satisfied.");
    }

    // Test to make sure addPerson() fails when personID is less than 10 characters.
    @Test
    @Order(3)
    public void testPersonIDWithLessThan10Characters_ShouldFail() throws IOException {
        Person person = new Person("56_d%&AB", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-11-2000");

        boolean result = person.addPerson();
        assertFalse(result, "Expected to fail when personID is less than 10 characters.");
    }

    // Test to make sure addPerson() fails when personID does not contain at least
    // two special characters.
    @Test
    @Order(4)
    public void testPersonIDOneSpecialCharacter_ShouldFail() throws IOException {
        Person person = new Person("56saduc&AB", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-11-2000");

        boolean result = person.addPerson();
        assertFalse(result, "Expected to fail when personID does not contain at least two special characters.");
    }

    // Test to make sure addPerson() fails when Person address does not have
    // 'Victoria' as the state.
    @Test
    @Order(5)
    public void testAddressStateNotVictoria_ShouldFail() throws IOException {
        Person person = new Person("56s_d%&fAB", "John", "Doe", "12|Main St|Melbourne|Queensland|Australia",
                "15-11-2000");

        boolean result = person.addPerson();
        assertFalse(result, "Expected to fail when address does not have 'Victoria' as the state.");
    }

    // Test to make sure addPerson() birthdate is in incorrect format.
    @Test
    @Order(6)
    public void testBirthdateIncorrectFormat_ShouldFail() throws IOException {
        Person person = new Person("56s_d%&fAB", "John", "Doe", "12|Main St|Melbourne|Victoria|Australia",
                "15-011-2000");

        boolean result = person.addPerson();
        assertFalse(result, "Expected to fail when birthdate is in the incorrect format ('DD/MM/YYYY')");
    }

    // -------------------- Teesha's addDemeritPoints() Test Cases
    // --------------------

    @Test
    @Order(7)
    public void testValidDemeritEntry_ShouldReturnSuccess() {
        Person person = new Person();
        person.setPersonID("56s_d%&fAB");
        person.setAge(25);
        String result = person.addDemeritPoints("01-06-2024", 3);
        assertEquals("Success", result, "Expected 'Success' for valid input.");
    }

    @Test
    @Order(8)
    public void testInvalidDateFormat_ShouldReturnFailed() {
        Person person = new Person();
        person.setPersonID("56s_d%&fAB");
        person.setAge(25);
        String result = person.addDemeritPoints("2024/06/01", 4);
        assertEquals("Failed", result, "Expected 'Failed' due to invalid date format.");
    }

    @Test
    @Order(9)
    public void testInvalidPointRange_ShouldReturnFailed() {
        Person person = new Person();
        person.setPersonID("56s_d%&fAB");
        person.setAge(25);
        String result = person.addDemeritPoints("05-06-2024", 0);
        assertEquals("Failed", result, "Expected 'Failed' due to point value out of range.");
    }

    @Test
    @Order(10)
    public void testOffenseDate_ShouldReturnFailed() {
        Person person = new Person();
        person.setPersonID("56s_d%&fAB");
        person.setAge(25);

        String firstResult = person.addDemeritPoints("10-06-2024", 2); // valid entry
        String secondResult = person.addDemeritPoints("10-06-2024", 3); // duplicate entry

        assertEquals("Failed", secondResult, "Expected 'Failed' due to duplicate offense date.");
    }

    @Test
    @Order(11)
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

    // Helper function to assist with simulating existing text files for unit
    // testing
    private void writeToFile(String content) throws IOException {
        File file = new File("persons.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.newLine();
        }
    }

}