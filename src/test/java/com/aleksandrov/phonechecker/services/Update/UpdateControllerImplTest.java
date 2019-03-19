package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.RegionOperatorPhoneCheckerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RegionOperatorPhoneCheckerApplication.class)
public class UpdateControllerImplTest {

    @Mock
    private DataUpdate dataUpdate;
    @Mock
    private Downloader downloader;
    @Mock
    private RegionsAndOperatorExtractor extractor;
    @Mock
    private RawStringHandler rawStringHandler;
    @Mock
    private DataSaver dataSaver;
    @Mock
    private IntervalsCleaner intervalsCleaner;
    @InjectMocks
    private UpdateController controller = new UpdateControllerImpl();
    private Queue<String> updateStatus = new LinkedList<>();
    private StringBuilder methodsStack = new StringBuilder();

    @Before
    public void setUp() {
        doNothing().when(dataUpdate).prepare();
        when(dataUpdate.getUpdateStatus()).thenReturn(updateStatus);
        doAnswer((a) -> {
            methodsStack.append("1");
            return null;
        }).when(extractor).extract();
        doAnswer((a) -> {
            methodsStack.append("2");
            return null;
        }).when(downloader).download();
        doAnswer((a) -> {
            methodsStack.append("3");
            return null;
        }).when(rawStringHandler).perform();
        doAnswer((a) -> {
            methodsStack.append("4");
            return null;
        }).when(dataSaver).save(intervalsCleaner);
        doAnswer((a) -> {
            methodsStack.append("5");
            return null;
        }).when(dataUpdate).endUpdate();
    }

    @Test
    public void performUpdate() {
        updateStatus.clear();
        controller.performUpdate();
        verify(dataUpdate).prepare();
        verify(extractor).extract();
        verify(downloader, atLeast(1)).download();
        verify(rawStringHandler, atLeast(1)).perform();
        verify(dataSaver, atLeast(1)).save(intervalsCleaner);
        verify(dataUpdate, atLeast(1)).endUpdate();
        assertTrue(updateStatus.size() > 0);
        assertTrue(methodsStack.toString().equals("122334455") || methodsStack.toString().equals("123234455"));
    }
}