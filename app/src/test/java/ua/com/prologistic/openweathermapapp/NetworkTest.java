package ua.com.prologistic.openweathermapapp;


import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import ua.com.prologistic.openweathermapapp.network.ConnectionAgent;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class NetworkTest {

    ConnectionAgent connectionAgent;

    @Before
    public void init() {
        connectionAgent = new ConnectionAgent();
    }

    @Test
    public void testCheckCorrectResponse() throws MalformedURLException {
        String fivedayforecst = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Mykolayiv&appid=747023b468051987827702abc50d22e6";
        String actual = connectionAgent.connectToUrl(new URL(fivedayforecst));
        String expected = "city";
        assertTrue(actual.contains(expected));
    }

    @Test(expected = IOException.class)
    public void testCheckInvalidRequest() throws MalformedURLException {
        String invalidRequestString = "http://api.openweathermap.org/data/2.5/forecast/daily?";
        String actual = connectionAgent.connectToUrl(new URL(invalidRequestString));
        String expected = "error";
        assertTrue(actual.contains(expected));
    }

    @Test
    public void testCheckRequestWithoutApiKey() throws MalformedURLException {
        String forecstRequest = "http://api.openweathermap.org/data/2.5/forecast/";
        String actual = connectionAgent.connectToUrl(new URL(forecstRequest));
        assertEquals("", actual);
    }
}
