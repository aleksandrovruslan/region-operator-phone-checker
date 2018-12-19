package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.utils.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@Service
@Scope("singleton")
public class DataUpdateImpl implements DataUpdate {
    @Autowired
    private ConfigProperties configProperties;

    /**
     * Queue with urls files for download.
     */
    private Queue<String> urlsQueue;

    /**
     * Queue of lists of lines for data processing and extract data.
     */
    private final Queue<List<String>> rawDownloadsStrings = new ConcurrentLinkedQueue<>();

    /**
     * Queue of lists of intervals for save to database.
     */
    private final Queue<List<PhoneInterval>> phoneIntervals = new ConcurrentLinkedQueue<>();

    /**
     * Map of phone operators extracted from database.
     * New phone operator before add to map, need save to database.
     */
    private final ConcurrentMap<String, PhoneOperator> operators = new ConcurrentHashMap<>();

    /**
     * Map of phone regions extracted from database.
     * New phone region before add to map, need save to database.
     */
    private final ConcurrentMap<String, PhoneRegion> regions = new ConcurrentHashMap<>();

    /**
     * Queue of strings with messages of update stage.
     */
    private final Queue<String> updateStatus = new ConcurrentLinkedQueue();

    /**
     * Complete the update and notify all unfinished threads.
     */
    private Runnable endUpdate;

    @Override
    public Queue<String> getUrlsQueue() {
        return urlsQueue;
    }

    public void setUrlsQueue(Queue<String> urlsQueue) {
        this.urlsQueue = urlsQueue;
    }

    @Override
    public Queue<List<String>> getRawDownloadsString() {
        return rawDownloadsStrings;
    }

    @Override
    public Queue<List<PhoneInterval>> getPhoneIntervals() {
        return phoneIntervals;
    }

    @Override
    public ConcurrentMap<String, PhoneOperator> getOperators() {
        return operators;
    }

    @Override
    public ConcurrentMap<String, PhoneRegion> getRegions() {
        return regions;
    }

    public Queue<String> getUpdateStatus() {
        return updateStatus;
    }

    @Override
    public void setEndUpdate(Runnable endUpdate) {
        this.endUpdate = endUpdate;
    }

    @Override
    public void endUpdate() {
        endUpdate.run();
    }

    @Override
    public void prepare() {
        urlsQueue = new ConcurrentLinkedQueue<>(
                configProperties.getDownloadUrl());
        updateStatus.clear();
    }

}
