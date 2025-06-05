package au.edu.rmit.sct.roadregistry;

import java.io.File;

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
    public void cleanUpPersonFile(){
        File file = new File("persons.txt");
        if(file.exists()) {
            boolean deleted = file.delete();
            System.out.println(deleted ? "Cleanup: persons.txt deleted." : "Cleanup: Failed to delete persons.txt.");
        }
    }
}