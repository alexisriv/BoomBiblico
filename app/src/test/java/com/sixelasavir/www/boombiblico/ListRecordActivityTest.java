package com.sixelasavir.www.boombiblico;

import com.sixelasavir.www.boombiblico.greendao.model.GamerRecord;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by alexis on 10/09/17.
 */
public class ListRecordActivityTest {

    private List<GamerRecord> gamerRecords;
    private ListRecordActivity activity;
    @Before
    public void beforeListRecordActivity() {
        activity = new ListRecordActivity();
        gamerRecords = new ArrayList<>();
        gamerRecords.add(new GamerRecord(1l,"Alexis1", 5, "0:11:234","aventureros",0,0));//3
        gamerRecords.add(new GamerRecord(2l,"Alexis2", 5, "0:11:234","aventureros",0,0));//3
        gamerRecords.add(new GamerRecord(3l,"Alexis3", 5, "0:11:666","aventureros",0,0));
        gamerRecords.add(new GamerRecord(4l,"Alexis4", 5, "0:10:145","aventureros",0,0));//1
        gamerRecords.add(new GamerRecord(5l,"Alexis5", 5, "0:10:415","aventureros",0,0));//2
        gamerRecords.add(new GamerRecord(6l,"Alexis6", 5, "0:11:601","aventureros",0,0));
        gamerRecords.add(new GamerRecord(7l,"Alexis7", 5, "0:10:145","aventureros",0,0));//1
        gamerRecords.add(new GamerRecord(8l,"Alexis8", 5, "0:11:234","aventureros",0,0));//3
        gamerRecords.add(new GamerRecord(9l,"Alexis9", 5, "0:11:234","aventureros",0,0));//3
        gamerRecords.add(new GamerRecord(10l,"Alexis10", 5, "0:15:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(11l,"Alexis11", 5, "0:15:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(12l,"Alexis12", 5, "0:15:234","aventureros",0,0));

        gamerRecords.add(new GamerRecord(13l,"Alexis13", 3, "0:10:100","aventureros",0,0));
        gamerRecords.add(new GamerRecord(14l,"Alexis14", 3, "0:10:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(15l,"Alexis15", 3, "0:10:400","aventureros",0,0));
        gamerRecords.add(new GamerRecord(16l,"Alexis16", 3, "0:10:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(17l,"Alexis17", 3, "0:10:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(18l,"Alexis18", 3, "0:10:500","aventureros",0,0));
        gamerRecords.add(new GamerRecord(19l,"Alexis19", 3, "0:10:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(20l,"Alexis20", 2, "0:10:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(21l,"Alexis21", 2, "0:10:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(22l,"Alexis22", 2, "0:10:200","aventureros",0,0));
        gamerRecords.add(new GamerRecord(23l,"Alexis23", 2, "0:10:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(24l,"Alexis24", 1, "0:10:234","aventureros",0,0));
        gamerRecords.add(new GamerRecord(25l,"Alexis25", 1, "0:10:231","aventureros",0,0));
        gamerRecords.add(new GamerRecord(26l,"Alexis26", 0, "0:10:232","aventureros",0,0));
        gamerRecords.add(new GamerRecord(27l,"Alexis27", 0, "0:10:234","aventureros",0,0));

    }


    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void onTabSelected() throws Exception {

    }

    @Test
    public void onTabUnselected() throws Exception {

    }

    @Test
    public void onTabReselected() throws Exception {

    }

    @Test
    public void onCreateOptionsMenu() throws Exception {

    }

    @Test
    public void onOptionsItemSelected() throws Exception {

    }

    @Test
    public void newLevel() throws Exception {

    }

    @Test
    public void orderPosition() throws Exception {

        gamerRecords = activity.orderPosition(gamerRecords);
        assertEquals(Integer.valueOf(1),gamerRecords.get(0).getPosition());
        assertEquals(Integer.valueOf(1),gamerRecords.get(1).getPosition());
        assertEquals("0:10:145",gamerRecords.get(0).getTimerRecordGamer());
        assertEquals("0:10:145",gamerRecords.get(1).getTimerRecordGamer());

        assertEquals(Integer.valueOf(2),gamerRecords.get(2).getPosition());
        assertEquals("0:10:415",gamerRecords.get(2).getTimerRecordGamer());

        assertEquals(Integer.valueOf(3),gamerRecords.get(4).getPosition());
        assertEquals(Integer.valueOf(3),gamerRecords.get(5).getPosition());
        assertEquals(Integer.valueOf(3),gamerRecords.get(6).getPosition());

        assertEquals("0:11:234",gamerRecords.get(4).getTimerRecordGamer());
        assertEquals("0:11:234",gamerRecords.get(4).getTimerRecordGamer());
        assertEquals("0:11:234",gamerRecords.get(4).getTimerRecordGamer());
    }

}