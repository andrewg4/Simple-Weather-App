package ua.com.prologistic.openweathermapapp;

import org.junit.Test;

import ua.com.prologistic.openweathermapapp.util.JsonParser;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertNotEquals;

public class JsonParserTest {

    @Test
    public void testGetValidDateString() throws Exception {
        int time = 1466359600;
        String actual = JsonParser.getValidDateString(time);
        String expected = "Вс, Июнь 19";

        assertEquals(expected, actual);
    }

    @Test
    public void testFailGetValidDateString() throws Exception {
        int time = 1466129600;
        String actual = JsonParser.getValidDateString(time);
        String expected = "Вс, Июнь 19";

        assertNotEquals(expected, actual);
    }

    @Test
    public void testInvalidDateString() throws Exception {
        int time = 0;
        String actual = JsonParser.getValidDateString(time);
        assertNotNull(actual);
    }

    @Test
    public void testAnotherInvalidDateString() throws Exception {
        int time = -123;
        String actual = JsonParser.getValidDateString(time);
        String expected = "Чт, Январь 1";
        assertSame(expected, actual);
    }

    @Test
    public void testCheckJsonString() throws Exception {
        boolean actual = JsonParser.checkJsonString("");
        boolean expected = false;
        assertNotEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testAnotherCheckJsonString() throws Exception {
        boolean actual = JsonParser.checkJsonString(null);
    }

    @Test
    public void testInvalidJsonString() throws Exception {
        boolean actual = JsonParser.checkJsonString("json");
        assertFalse(actual);
    }

    @Test
    public void testValidJsonString() throws Exception {
        boolean actual = JsonParser.checkJsonString("Not found city");
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void testAnotherValidJsonString() throws Exception {
        boolean actual = JsonParser.checkJsonString("");
        boolean expected = true;
        assertEquals(expected, actual);
    }
}