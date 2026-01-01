package com.devops;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testGetMessage() {
        String expected = "Bonjour et bon courage dans votre projet en DevOps";
        String actual = App.getMessage();
        assertEquals("Le message devrait correspondre", expected, actual);
    }

    @Test
    public void testMessageNotNull() {
        assertNotNull("Le message ne devrait pas être null", App.getMessage());
    }

    @Test
    public void testMessageNotEmpty() {
        assertTrue("Le message ne devrait pas être vide",
                App.getMessage().length() > 0);
    }
}