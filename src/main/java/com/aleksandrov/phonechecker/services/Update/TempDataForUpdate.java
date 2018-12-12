package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public interface TempDataForUpdate {
    BlockingQueue<List<String>> getRawDownloadsString();
    BlockingQueue<List<PhoneInterval>> getPhoneIntervals();
    ConcurrentMap<String, PhoneOperator> getOperators();
    ConcurrentMap<String, PhoneRegion> getRegions();
    void setEndUpdate(Runnable endUpdate);
    void endUpdate();
}
