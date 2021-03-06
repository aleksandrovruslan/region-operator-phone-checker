package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.utils.LinksParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@Service
public class DataUpdateImpl implements DataUpdate {

    private LinksParser linksParser;

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

    @Autowired
    public DataUpdateImpl(LinksParser linksParser) {
        this.linksParser = linksParser;
    }

    @Override
    public Queue<String> getUrlsQueue() {
        return urlsQueue;
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
        urlsQueue = linksParser.getLinks();
        updateStatus.clear();
    }

}
