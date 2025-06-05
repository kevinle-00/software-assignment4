package au.edu.rmit.sct.roadregistry;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
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
    private int age;
    private HashMap<String, Integer> demeritPoints; // offenseDate -> demerit points
    private boolean isSuspended;

    public Person() { // Default Constructor for the Person class
        this.personID = null;
        this.firstName = null;
        this.lastName = null;
        this.address = null;
        this.birthdate = null;
        this.demeritPoints = new HashMap<>();
        this.isSuspended = false;
        this.age = 25;
    }

    public Person(String personID, String firstName, String lastName, String address, String birthdate) { // Constructor
                                                                                                          // for the
                                                                                                          // Person
                                                                                                          // class

        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthdate = birthdate;

    }

    // |----------------- addPerson() - Jack -----------------|

    public boolean addPerson() {

        if (!checkPersonID() || !checkAddress() || !checkBirthdate()) {
            return false;
        }

        String filePath = "persons.txt";

        String personInfo = personID + "," + firstName + "," + lastName + "," + address + "," + birthdate + "\n";

        try(FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

                bufferedWriter.write(personInfo);
                return true;

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            
    }

    // Helper Methods
    
    //  Condition 1
    private boolean checkPersonID() { // Function to evaluate if the given personID meets each criteria of condition 1
        if (this.personID != null) {
            if (!personIDLength() || !personIDNumbers() || !personIDSpecialCharacters() || !personIDUpperCase()) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    private boolean personIDLength() { // Checks if the personID is exactly 10 characters long
        if (personID.length() != 10) {
            return false;
        }

        return true;
    }

    private boolean personIDNumbers() { // Checks if the first two characters are numbers between 2 and 9

        char firstChar = this.personID.charAt(0); // First Character
        char secondChar = this.personID.charAt(1); // Second Character

        int intFirst = Character.getNumericValue(firstChar); // Convert to int
        int intSecond = Character.getNumericValue(secondChar);

        if (intFirst < 2 || intFirst >= 9 || intSecond <= 2 || intSecond > 9) {
            return false;
        }

        return true;
    }

    private boolean personIDSpecialCharacters() { // Checks if there are at least two special characters between
                                                  // characters 3 and 8
        int specialCount = 0;

        for (int i = 2; i < this.personID.length() - 2; i++) {
            if (!Character.isLetterOrDigit(this.personID.charAt(i))) {
                specialCount++;
            }
        }

        if (specialCount < 2) {
            return false;
        }

        return true;
    }

    private boolean personIDUpperCase() {
        char secondLast = this.personID.charAt(this.personID.length() - 2); // Second last character
        char last = this.personID.charAt(this.personID.length() - 1); // Last character

        if (!Character.isAlphabetic(last) || !Character.isAlphabetic(secondLast)) {
            return false;
        }

        if (!Character.isUpperCase(last) || !Character.isUpperCase(secondLast)) {
            return false;
        }

        return true;
    }

    // Condition 2
    private boolean checkAddress() { // Check if address is in the right format 'Street
                                     // Number|Street|City|State|Country'

        if (this.address == null) {
            return false;
        }

        String[] addressParts = this.address.split("\\|");

        if (addressParts.length != 5) {
            return false;
        }

        if (!addressParts[3].equals("Victoria")) {
            return false;
        }

        return true;
    }

    // Condition 3
    private boolean checkBirthdate() { // Check if birthdate is in the correct format 'DD-MM-YYYY'

        if (this.birthdate == null) {
            return false;
        }

        String[] birthdateParts = this.birthdate.split("-");

        if (birthdateParts[0].length() > 2) {
            return false;
        }

        if (birthdateParts[1].length() > 2) {
            return false;
        }

        if (birthdateParts[2].length() > 4) {
            return false;
        }

        return true;
    }

    // |----------------- updatePersonalDetails() - Kevin -----------------|

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

                // Create oldPerson object using parsed fields
                Person oldPerson = new Person(existingID, existingFirst, existingLast, existingAddress,
                        existingBirthdate);

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

    // Helper Methods

    private boolean canUpdateAddress(Person oldPerson, Person newPerson) {
        int age = calculateAgeFromBirthday(oldPerson.getBirthdate());
        return age >= 18;
    }

    private boolean isValidBirthdayChange(Person oldPerson, Person newPerson) {
        return oldPerson.getPersonID().equals(newPerson.getPersonID()) &&
                oldPerson.getFirstName().equals(newPerson.getFirstName()) &&
                oldPerson.getLastName().equals(newPerson.getLastName()) &&
                oldPerson.getAddress().equals(newPerson.getAddress());

    }

    private int calculateAgeFromBirthday(String birthdate) {
        // TODO: Implementation
        return 0;
    }

    private boolean canUpdateID(String oldID) {
        char firstChar = oldID.charAt(0);
        return !Character.isDigit(firstChar) || ((firstChar - '0') % 2 != 0);
    }

    private boolean isChanging(String oldValue, String newValue) {
        return !Objects.equals(oldValue, newValue);
    }

    private boolean isChangingBirthday(Person oldPerson, Person newPerson) {
        return isChanging(oldPerson.getBirthdate(), newPerson.getBirthdate());
    }

    private boolean isChangingID(Person oldPerson, Person newPerson) {
        return isChanging(oldPerson.getPersonID(), newPerson.getPersonID());
    }

    private boolean isChangingAddress(Person oldPerson, Person newPerson) {
        return isChanging(oldPerson.getAddress(), newPerson.getAddress());
    }

    // |----------------- addDemeritPoints() - Teesha -----------------|

    public String addDemeritPoints(String dateOfOffense, int points) {
        // Condition 1: Check DD-MM-YYYY format
        if (!isValidDateFormat(dateOfOffense)) {
            return "Failed";
        }
        //Condition 2: Check Points range
        if (points < 1 || points > 6) {
            return "Failed";
        }
        // Reject duplicate entries
        if (demeritPoints.containsKey(dateOfOffense)) {
            return "Failed";
        }
        // Store the offense
        demeritPoints.put(dateOfOffense, points);

        //Condition 3: Check Offenses within the last 2 years 
        int totalPoints = 0;
        LocalDate now = LocalDate.now();
        for (String dateStr : demeritPoints.keySet()) {
            // Parse day, month, year manually
            int day = Integer.parseInt(dateStr.substring(0, 2));
            int month = Integer.parseInt(dateStr.substring(3, 5));
            int year = Integer.parseInt(dateStr.substring(6, 10));

            LocalDate offenseDate = LocalDate.of(year, month, day);

            int yearDiff = now.getYear() - offenseDate.getYear();

            if (yearDiff < 2 || (yearDiff == 2 &&
                (now.getMonthValue() < offenseDate.getMonthValue() ||
                (now.getMonthValue() == offenseDate.getMonthValue() && now.getDayOfMonth() < offenseDate.getDayOfMonth()))
            )) {
                totalPoints += demeritPoints.get(dateStr);
            }
        }
        //Suspension rules based on age
        if (age < 21 && totalPoints > 6) {
            isSuspended = true;
        } else if (age >= 21 && totalPoints > 12) {
            isSuspended = true;
        }

        return "Success";
    }

    
    // |----------------- Getter and Setter methods -----------------|

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public HashMap<java.time.LocalDate, Integer> getDemeritPoints() {
        return demeritPoints;
    }

    public void setDemeritPoints(HashMap<java.time.LocalDate, Integer> demeritPoints) {
        this.demeritPoints = demeritPoints;
    }

}