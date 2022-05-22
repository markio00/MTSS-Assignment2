package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    
    private User user;

    @Before
    public void testUserSetup() {
        // Arrange
        this.user = new User("user1", "Mario", "Rossi", LocalDate.of(2000,04,19));
    }

    @Test
    public void testGetBirthDate() {
        // Act
        LocalDate birthDate = this.user.getBirthDate();

        // Assert
        assertEquals(LocalDate.of(2000,04,19), birthDate);
    }

    @Test
    public void testGetId() {
        // Act
        String id = this.user.getId();

        // Assert
        assertEquals("user1", id);
    }

    @Test
    public void testGetName() {
        // Act
        String name = this.user.getName();

        // Assert
        assertEquals("Mario", name);
    }

    @Test
    public void testGetSurname() {
        // Act
        String surname = this.user.getSurname();

        // Assert
        assertEquals("Rossi", surname);
    }



}

