package com.example.service;

import com.example.model.Choices;
import com.example.model.GameState;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MathServiceTest {

    @Test
    void prime() {
        assertEquals(1, MathService.isPrime(7));
        assertEquals(0, MathService.isPrime(8));
        assertEquals(0, MathService.isPrime(1));
        assertEquals(0, MathService.isPrime(-5));
    }

    @Test
    void mod() {
        assertEquals(2, MathService.calculateMod(17, 5));
        assertEquals(-2, MathService.calculateMod(-17, 5));
        assertEquals(0, MathService.calculateMod(0, 5));
        assertThrows(IllegalArgumentException.class, () -> MathService.calculateMod(5, 0));
    }

    @Test
    void trig() {
        assertEquals(0.5, MathService.calculateSin(30), 1e-9);
        assertEquals(0.5, MathService.calculateCos(60), 1e-9);
        assertEquals(1.0, MathService.calculateTan(45), 1e-9);
        assertTrue(Double.isNaN(MathService.calculateTan(90)));
    }

    @Test
    void intChoicesStayWithin20() {
        for (int i = 0; i < 1000; i++) {
            Choices c = MathService.intChoices(5);
            assertEquals(3, c.getOptions().length);
            assertEquals("5", c.getCorrectText());
            assertEquals(3, new HashSet<>(Arrays.asList(c.getOptions())).size());
            for (String o : c.getOptions()) {
                assertTrue(Math.abs(Integer.parseInt(o) - 5) <= 20);
            }
        }
    }

    @Test
    void decimalChoicesStayWithin20() {
        for (int i = 0; i < 1000; i++) {
            Choices c = MathService.decimalChoices(0.5);
            assertEquals(3, c.getOptions().length);
            assertEquals("0.50", c.getCorrectText());
            assertEquals(3, new HashSet<>(Arrays.asList(c.getOptions())).size());
            for (String o : c.getOptions()) {
                assertTrue(Math.abs(Double.parseDouble(o) - 0.5) <= 20.0001);
            }
        }
    }

    @Test
    void scoring() {
        GameState perfect = new GameState("P");
        for (int i = 0; i < 4; i++) {
            Choices c = MathService.intChoices(1);
            perfect.startChoosing("q", c);
            perfect.answer(c.getCorrectIndex());
        }
        assertTrue(perfect.isFinished());
        assertEquals(10, perfect.getScore());

        GameState wrong = new GameState("P");
        for (int i = 0; i < 4; i++) {
            Choices c = MathService.intChoices(1);
            wrong.startChoosing("q", c);
            wrong.answer((c.getCorrectIndex() + 1) % 3);
        }
        assertEquals(0, wrong.getScore());
    }
}
