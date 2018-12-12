package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Scope("singleton")
public class TempDataForUpdateImpl implements TempDataForUpdate {
    private final BlockingQueue<List<String>> rawDownloadsStrings = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<PhoneInterval>> phoneIntervals = new LinkedBlockingQueue<>();
    private final ConcurrentMap<String, PhoneOperator> operators = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, PhoneRegion> regions = new ConcurrentHashMap<>();
    private Runnable endUpdate;

    @Override
    public BlockingQueue<List<String>> getRawDownloadsString() {
        return rawDownloadsStrings;
    }

    @Override
    public BlockingQueue<List<PhoneInterval>> getPhoneIntervals() {
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

    @Override
    public void setEndUpdate(Runnable endUpdate) {
        this.endUpdate = endUpdate;
    }

    @Override
    public void endUpdate() {

    }
}
