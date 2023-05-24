import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {

    @Test
    public void nullHorsesExceptionAndMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));

        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    public void emptyHorsesExceptionAndMessage() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));

        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    public void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 1; i < 30; i++) {
            horses.add(new Horse("" + i, 1, 1));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public void move() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 1; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        new Hippodrome(horses).move();

        for (Horse horse : horses) {
            verify(horse).move();
        }

    }

    @Test
    public void getWinnerTest(){
        Horse horse1 = new Horse("Raz", 1,31);
        Horse horse2 = new Horse("Da", 1,10);
        Horse horse3 = new Horse("Tri", 1,5);
        Horse horse4 = new Horse("Chetyry", 1,41);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1,horse2,horse3,horse4));

        assertSame(horse4,hippodrome.getWinner());

    }

}
