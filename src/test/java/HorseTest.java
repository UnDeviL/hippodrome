import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    @Test
    public void nullNameException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse(null, 1, 1));
    }

    @Test
    public void nullNameExceptionMessage() {
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n\n\n\n"})
    public void blankNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void negativeSpeedException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse("Peter", -1, 1));

    }

    @Test
    public void negativeSpeedExceptionMessage() {
        try {
            new Horse("Alex", -1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void negativeDistanceException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Horse("Peter", 1, -1));

    }

    @Test
    public void negativeDistanceExceptionMessage() {
        try {
            new Horse("Alex", 1, -1);
        } catch (IllegalArgumentException e) {
            assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getNameTest() {
        Horse horse = new Horse("horseName", 1, 1);
        String expectedName = "Petrovka";
        try {
            Field name = Horse.class.getDeclaredField("name");
            name.setAccessible(true);
            name.set(horse, expectedName);

            assertEquals("Petrovka", expectedName);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void getSpeedTest() {
        Horse horse = new Horse("horseName", 4, 1);

        assertEquals(4, horse.getSpeed());
    }

    @Test
    public void getDistanceTest() {
        Horse horse = new Horse("horseName", 1, 2);

        assertEquals(2, horse.getDistance());
    }

    @Test
    public void getZeroDistanceByDefault() {
        Horse horse = new Horse("Olejka", 1);
        int expectedResult = 0;

        try {
            Field name = Horse.class.getDeclaredField("distance");
            name.setAccessible(true);
            name.set(horse, expectedResult);

            assertEquals(0, expectedResult);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void moveUsesGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Volodia", 41, 231).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    void moveTest(double random){

        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            Horse horse = new Horse("Olejka",22, 222);
            mockedStatic.when(() ->Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);
            horse.move();
            assertEquals(222+22*random,horse.getDistance());
        }

    }
}