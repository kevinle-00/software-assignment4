package au.edu.rmit.sct.roadregistry;

import java.util.Date;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints; // offenseDate -> demerit points
    private boolean isSuspended;

    public boolean addPerson() {
        // Indicate that are implementing this here:
        return true;
    }

    public boolean updatePersonalDetails(String originalPersonID, Person updatedPerson) {
        // Kevin
        // Condition 1: If a person is under 18, their address cannot be changed.
        // Condition 2: If a person's birthday is going to be changed, then no other
        // personal detail (i.e, person's ID, firstName, lastName, address) can be
        // changed.
        // Condition 3: If the first character/digit of a person's ID is an even number,
        // then their ID cannot be changed.
        File inputFile = new File("persons.txt"); // TXT file of person
        File tempFile = new File("persons_temp.txt"); // Temporary TXT file of person used for updates.

        boolean updated = false; // Track whether or not update was successful or not.

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Split the line into fields
                String[] fields = line.split(",", -1); // -1 keeps empty values if any

                if (fields.length != 5) { // Check if line contained exactly 5 fields, if not, then the line was
                                          // inputted incorrectly.
                    writer.write(line); // If incorrect, write to temp file to preserve the data.
                    writer.newLine();
                    continue; // Move on from this line as no updates should be made.
                }

                // Parse fields
                String existingID = fields[0];
                String existingFirst = fields[1];
                String existingLast = fields[2];
                String existingAddress = fields[3];
                String existingBirthdate = fields[4];

                // Check if this is the person to update, if not, move on
                if (!existingID.equals(originalPersonID)) {
                    writer.write(line); // Preserve data
                    writer.newLine();
                    continue; // Move on
                }

                // TODO: Condition 1, 2 and 3
            }

        } catch (IOException e) { // Exception handling
            System.err.println("Error reading or writing files: " + e.getMessage());
            return false;
        }

        return updated;
    }

    public String addDemeritPoints() {
        // Indicate that you are implementing this here:
        return "Success";
    }
}