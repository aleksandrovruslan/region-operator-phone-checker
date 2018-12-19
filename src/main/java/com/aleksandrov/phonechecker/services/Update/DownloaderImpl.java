package com.aleksandrov.phonechecker.services.Update;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class DownloaderImpl implements Downloader {
    @Autowired
    private DataUpdate dataUpdate;

    private static final Logger log = LogManager.getLogger(DownloaderImpl.class);

    @Override
    public void download() {
        dataUpdate.getUpdateStatus().add("Start download " + new Date());
        try {
            String url = dataUpdate.getUrlsQueue().poll();
            while (url != null) {
                dataUpdate.getRawDownloadsString().add(getRawStringFromCSVLink(url));
                dataUpdate.getUpdateStatus().add("File " + url + " downloaded " + new Date());
                url = dataUpdate.getUrlsQueue().poll();
            }
        } catch (IOException e) {
            log.error(e);
            dataUpdate.getUpdateStatus().add(e.getMessage() + " " + new Date());
            dataUpdate.endUpdate();
        }
        dataUpdate.getUpdateStatus().add("End download " + new Date());
    }

    private List<String> getRawStringFromCSVLink(String link) throws IOException {
        List<String> rawStrings = new LinkedList<>();
        URL url = new URL(link);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                url.openStream(), "windows-1251"))) {
            String rawString = reader.readLine();
            while (rawString != null) {
                rawStrings.add(rawString);
                rawString = reader.readLine();
            }
        }
        return rawStrings;
    }
}