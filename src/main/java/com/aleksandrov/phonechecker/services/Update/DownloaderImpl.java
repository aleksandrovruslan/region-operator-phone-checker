package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.utils.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Service
public class DownloaderImpl implements Downloader{
    @Autowired
    private ConfigProperties configProperties;

    @Override
    public void download(List<String> updateStatusList, Thread thread, Runnable endUpdateState, BlockingQueue<List<String>> rawStrings) {
        updateStatusList.add("Start download " + new Date());
        for (String s:configProperties.getDownloadUrl()) {
            rawStrings.add(getRawStringFromCSVLink(s));
            updateStatusList.add("Link \"" + s + "\" downloaded " + new Date());
        }
        updateStatusList.add("End download " + new Date());
    }

    private List<String> getRawStringFromCSVLink(String link) {
        List<String> rawStrings = new LinkedList<>();
        try {
            URL url = new URL(link);
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                    url.openStream(), "windows-1251"))) {
                String rawString = reader.readLine();
                while (rawString != null) {
                    rawStrings.add(rawString);
                    rawString = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return rawStrings;
    }
}