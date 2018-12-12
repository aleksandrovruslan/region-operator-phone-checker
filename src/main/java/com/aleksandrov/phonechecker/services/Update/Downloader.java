package com.aleksandrov.phonechecker.services.Update;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface Downloader {
    void download(List<String> updateStatusList, Thread thread
            , Runnable endUpdateState, BlockingQueue<List<String>> rawStrings);
}
