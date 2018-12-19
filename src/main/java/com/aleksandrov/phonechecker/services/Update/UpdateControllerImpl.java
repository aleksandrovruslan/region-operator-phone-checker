package com.aleksandrov.phonechecker.services.Update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.Phaser;

@Service
public class UpdateControllerImpl implements UpdateController {
    @Autowired
    private DataUpdate dataUpdate;
    @Autowired
    private Downloader downloader;
    @Autowired
    private RegionsAndOperatorExtractor extractor;
    @Autowired
    private RawStringHandler rawStringHandler;
    @Autowired
    private DataSaver dataSaver;
    @Autowired
    private IntervalsCleaner intervalsCleaner;

    private Phaser phaser = new Phaser(2);

    @Override
    public void performUpdate() {
        dataUpdate.prepare();
        dataUpdate.getUpdateStatus().add("Start update " + new Date());
        new Thread(() -> {
            extractor.extract();
            run();
        }).start();
        new Thread(() -> run()).start();
    }

    private void run() {
        downloader.download();
        phaser.arrive();
        rawStringHandler.perform();
        phaser.arriveAndAwaitAdvance();
        dataSaver.save(intervalsCleaner);
        phaser.arriveAndAwaitAdvance();
        dataUpdate.endUpdate();
        phaser.arriveAndDeregister();
    }
}
