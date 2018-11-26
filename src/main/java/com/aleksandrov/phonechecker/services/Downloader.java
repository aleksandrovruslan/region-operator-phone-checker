package com.aleksandrov.phonechecker.services;

import java.util.List;

public interface Downloader {
    void download(Boolean updateState, List<String> updateStatusList, ReaderCSV readerCSV);
}
