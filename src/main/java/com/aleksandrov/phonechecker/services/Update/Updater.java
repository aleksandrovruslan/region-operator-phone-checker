package com.aleksandrov.phonechecker.services.Update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope("singleton")
public class Updater {
    private volatile boolean updateState = false;
    private volatile List<String> updateStatusList = new ArrayList<>();

    @Autowired
    private UpdateController controller;


    public List<String> startUpdate() {
        if (!updateState) {
            synchronized (Updater.class) {
                if (!updateState) {
                    updateState = true;
                    Thread thread = new Thread(() -> controller.performUpdate(
                            updateStatusList, () -> endUpdateState()));
                    thread.start();
                }
            }
        }
        return updateStatusList;
    }

    public List<String> getUpdateStatusList() {
        return updateStatusList;
    }

    public boolean isUpdateState() {
        return updateState;
    }

    private void endUpdateState() {
        updateStatusList.add("Complete the update. " + new Date());
        updateState = false;
    }
}
