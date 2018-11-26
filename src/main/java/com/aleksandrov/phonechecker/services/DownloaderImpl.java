package com.aleksandrov.phonechecker.services;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class DownloaderImpl implements Downloader{
    //TODO put in order constants
    public static final String DOWNLOAD_URL_1 =
            "https://rossvyaz.ru/opendata/7710549038-Rosnumbase/Kody_ABC-3kh.csv";
    public static final String INTERVAL1_CSV = "interval1.csv";

    public static final String DOWNLOAD_URL_2 =
            "https://rossvyaz.ru/opendata/7710549038-Rosnumbase/Kody_ABC-4kh.csv";
    public static final String INTERVAL2_CSV = "interval2.csv";

    public static final String DOWNLOAD_URL_3 =
            "https://rossvyaz.ru/opendata/7710549038-Rosnumbase/Kody_ABC-8kh.csv";
    public static final String INTERVAL3_CSV = "interval3.csv";

    public static final String DOWNLOAD_URL_4 =
            "https://rossvyaz.ru/opendata/7710549038-Rosnumbase/Kody_DEF-9kh.csv";
    public static final String INTERVAL4_CSV = "interval4.csv";

    public static final String SAVE_PATH = "src/main/resources/static/";

    @Override
    public void download(Boolean updateState, List<String> updateStatusList, ReaderCSV readerCSV){
        try {
            download(DOWNLOAD_URL_1, INTERVAL1_CSV);
            download(DOWNLOAD_URL_2, INTERVAL2_CSV);
            download(DOWNLOAD_URL_3, INTERVAL3_CSV);
            download(DOWNLOAD_URL_4, INTERVAL4_CSV);
            readerCSV.read();
        } catch (IOException e) {
            e.printStackTrace();
            updateState = false;
            updateStatusList.add("download error: " + e.getMessage());
        }
    }

    public void download(String downloadURL, String fileName) throws IOException {
        URL url = new URL(downloadURL);
        File file = new File(SAVE_PATH + fileName);
        FileUtils.copyURLToFile(url, file);
    }
}