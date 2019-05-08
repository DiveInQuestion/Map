package com.example.a19360.map;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SongsCollectionOperaterTest {
    SongsCollectionOperator songsCollectionOperator;
    ArrayList<Songs> songsCollection;
    @Before
    public void setUp() throws Exception {
        songsCollectionOperator =new SongsCollectionOperator();
        Context context= InstrumentationRegistry.getTargetContext();
        songsCollection = songsCollectionOperator.load(context);
    }

    @After
    public void tearDown() throws Exception {
         Context context= InstrumentationRegistry.getTargetContext();
         songsCollectionOperator.save(context, songsCollection);
    }

    @Test
    public void loadAndSave() throws Exception {
        ArrayList<Songs> mySongsCollection=new ArrayList<Songs>();
        for(int i=0;i<10;i++) {
            Songs songs = new Songs();
            songs.setName("Horizon");
            mySongsCollection.add(songs);
        }
        Songs songs = new Songs();
        songs.setName("Insomnia");
        mySongsCollection.add(songs);

        Context context= InstrumentationRegistry.getTargetContext();
        Assert.assertTrue( songsCollectionOperator.save(context,mySongsCollection));

        ArrayList<Songs>songsCollection= songsCollectionOperator.load(context);
        Assert.assertNotNull(songsCollection);
        Assert.assertEquals(11,songsCollection.size());
        Songs songs1=songsCollection.get(0);
        Assert.assertEquals("Horizon",songs1.getName());
        Songs songs10=songsCollection.get(9);
        Assert.assertEquals("Horizon",songs10.getName());
        Songs songs2=songsCollection.get(10);
        Assert.assertEquals("Insomnia",songs2.getName());
    }
    @Test
    public void loadAndSaveNoItem() throws Exception {
        ArrayList<Songs> mySongsCollection=new ArrayList<Songs>();

        Context context= InstrumentationRegistry.getTargetContext();
        Assert.assertTrue( songsCollectionOperator.save(context,mySongsCollection));

        ArrayList<Songs> songsCollection= songsCollectionOperator.load(context);
        Assert.assertNotNull(songsCollection);
        Assert.assertEquals(0,songsCollection.size());
    }
}