package au.edu.rmit.sct.roadregistry;
import java.io.File;

public class Main {
    public static void main(String[] args) {

        Person person = new Person(
                "56s_d%&fAB",
                "John",
                "Doe",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990");

        person.addPerson();

        File file = new File("persons.txt");
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("persons.txt deleted after run.");
            } else {
                System.out.println("Failed to delete persons.txt.");
            }
        }

    }
}
