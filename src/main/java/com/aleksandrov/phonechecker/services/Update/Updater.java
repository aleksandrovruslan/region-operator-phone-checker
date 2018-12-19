package com.aleksandrov.phonechecker.services.Update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope("singleton")
public class Updater {
    @Autowired
    private UpdateController controller;
    private DataUpdate dataUpdate;

    private volatile boolean updateState;

    @Autowired
    public Updater(DataUpdate dataUpdate) {
        this.dataUpdate = dataUpdate;
        dataUpdate.setEndUpdate(this::endUpdateState);
    }

    public Iterable<String> startUpdate() {
        if (!updateState) {
            synchronized (Updater.class) {
                if (!updateState) {
                    updateState = true;
                    new Thread(() -> controller.performUpdate()).start();
                }
            }
        }
        return dataUpdate.getUpdateStatus();
    }

    public Iterable<String> getUpdateStatusList() {
        return dataUpdate.getUpdateStatus();
    }

    public boolean isUpdateState() {
        return updateState;
    }

    private synchronized void endUpdateState() {
        dataUpdate.getUpdateStatus().add("Complete the update. " + new Date());
        if (updateState) {
            updateState = false;
        }
    }
}
