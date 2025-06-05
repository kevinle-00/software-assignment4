package au.edu.rmit.sct.roadregistry;
import java.io.File;

public class Main {
    public static void main(String[] args) {


        File file = new File("persons.txt");
        if (file.exists()) {
            if (file.delete()) {
                System.out.println(" Existing persons.txt deleted before run.");
            } else {
                System.out.println("No existing persons.txt to delete.");
            }
        }

        Person person = new Person(
                "56s_d%&fA",
                "John",
                "Doe",
                "32|Highland Street|Melbourne|Victoria|Australia",
                "15-11-1990");

        boolean result = person.addPerson();

        System.out.println(result);

    
    }
}
