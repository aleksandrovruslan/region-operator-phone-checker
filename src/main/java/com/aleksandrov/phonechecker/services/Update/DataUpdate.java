package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentMap;

public interface DataUpdate {

    Queue<String> getUrlsQueue();

    Queue<List<String>> getRawDownloadsString();

    Queue<List<PhoneInterval>> getPhoneIntervals();

    ConcurrentMap<String, PhoneOperator> getOperators();

    ConcurrentMap<String, PhoneRegion> getRegions();

    Queue<String> getUpdateStatus();

    void setEndUpdate(Runnable endUpdate);

    void endUpdate();

    void prepare();

}
