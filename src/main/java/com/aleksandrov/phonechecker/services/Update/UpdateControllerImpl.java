package com.aleksandrov.phonechecker.services.Update;
import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class UpdateControllerImpl implements UpdateController {
    @Autowired
    private Downloader downloader;
    @Autowired
    private RegionsAndOperatorExtractor extractor;

    private final BlockingQueue<List<String>> rawStrings = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<PhoneInterval>> phoneIntervals = new LinkedBlockingQueue<>();
    private final ConcurrentMap<String, PhoneOperator> operators = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, PhoneRegion> regions = new ConcurrentHashMap<>();

    @Override
    public void performUpdate(List<String> updateStatusList, Runnable endUpdateState) {
        updateStatusList.clear();
        updateStatusList.add("Start update " + new Date());
        Thread threadExtractor = new Thread(() ->
                extractor.extract(updateStatusList, rawStrings,
                        phoneIntervals, operators, regions,endUpdateState));
        Thread threadDownloader = new Thread(() -> downloader
                .download(updateStatusList, threadExtractor, endUpdateState, rawStrings));
        threadExtractor.start();
        threadDownloader.start();
    }
}
