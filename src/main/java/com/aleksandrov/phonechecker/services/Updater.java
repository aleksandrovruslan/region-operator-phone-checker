package com.aleksandrov.phonechecker.services;

import java.util.ArrayList;
import java.util.List;

public class Updater {

    private volatile Boolean updateState = false;

    private volatile List<String> updateStatusList = new ArrayList<>();

    private static Updater ourInstance = new Updater();

    private Updater() {
    }

    public static Updater getInstance() {
        return ourInstance;
    }

    public synchronized List<String> start(Downloader downloader, ReaderCSV readerCSV) {
        if (!updateState) {
            updateState = true;
            updateStatusList.clear();
            updateStatusList.add("Start update");
            Thread thread = new Thread(() -> download(
                    updateState, updateStatusList, downloader, readerCSV));
            thread.start();
        }
        return updateStatusList;
    }

    public List<String> getUpdateStatusList() {
        return updateStatusList;
    }

    private void download(Boolean updateState, List<String> updateStatusList
            , Downloader downloader, ReaderCSV readerCSV) {
        downloader.download(updateState, updateStatusList, readerCSV);
    }

}
