package com.example.a19360.map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SongsTest {
    Songs songs;
    @Before
    public void setUp() throws Exception {
        songs =new Songs();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void setName() throws Exception {
        String name="Horizon";
        songs.setName(name);
        Assert.assertEquals(name, songs.getName());
    }

    @Test
    public void getPictureId() throws Exception {

    }

    @Test
    public void setPictureId() throws Exception {

    }

    @Test
    public void getNum() throws Exception {

    }

    @Test
    public void setNum() throws Exception {

    }

}