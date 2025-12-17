package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainTest {
    @Test
    void testMainClassExists() {
        Main main = new Main();
        assertNotNull(main, "Main class should not be null");
    }
}
