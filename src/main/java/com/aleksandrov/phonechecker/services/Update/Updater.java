package com.aleksandrov.phonechecker.services.Update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Queue;

@Service
public class Updater {

    private UpdateController controller;
    private DataUpdate dataUpdate;
    private volatile boolean updateState;

    @Autowired
    public Updater(DataUpdate dataUpdate, UpdateController controller) {
        this.dataUpdate = dataUpdate;
        this.controller = controller;
        dataUpdate.setEndUpdate(this::endUpdateState);
    }

    public Queue<String> startUpdate() {
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

    public Queue<String> getUpdateStatusList() {
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