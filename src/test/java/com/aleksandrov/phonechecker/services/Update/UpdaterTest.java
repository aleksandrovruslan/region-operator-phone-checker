package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.RegionOperatorPhoneCheckerApplication;
import com.aleksandrov.phonechecker.RegionOperatorPhoneCheckerApplicationTests;
import com.aleksandrov.phonechecker.utils.LinksParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Queue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RegionOperatorPhoneCheckerApplication.class)
public class UpdaterTest {

    @Mock
    private UpdateController controller;
    @Mock
    private LinksParser linksParser;
    private DataUpdate dataUpdate;
    private Updater updater;
    private String status = "end update";

    @Before
    public void before() {
        dataUpdate = new DataUpdateImpl(linksParser);
        updater = new Updater(dataUpdate, controller);
        doAnswer((a) -> {
            dataUpdate.getUpdateStatus().add(status);
            return null;
        }).when(controller).performUpdate();
    }

    @Test
    public void when_started_non_running_update() {
        dataUpdate.getUpdateStatus().clear();
        Queue<String> expectedStatus = updater.startUpdate();
        verify(controller, times(1)).performUpdate();
        assertEquals(expectedStatus.size(), 1);
        assertEquals(expectedStatus.peek(), status);
        assertTrue(updater.isUpdateState());
    }

    @Test
    public void when_started_when_running_update() {
        dataUpdate.getUpdateStatus().clear();
        updater.startUpdate();
        Queue<String> expectedStatus = updater.startUpdate();
        verify(controller, timeout(1000).times(1)).performUpdate();
        assertEquals(expectedStatus.size(), 1);
        assertEquals(expectedStatus.peek(), status);
        assertTrue(updater.isUpdateState());
    }

    @Test
    public void when_started_after_completion_update() {
        dataUpdate.getUpdateStatus().clear();
        updater.startUpdate();
        dataUpdate.endUpdate();
        Queue<String> expectedStatus = updater.startUpdate();
        verify(controller, timeout(1000).times(2)).performUpdate();
        assertEquals(expectedStatus.size(), 3);
        assertTrue(updater.isUpdateState());
    }

}