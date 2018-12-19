package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.services.PhoneIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataSaverImpl implements DataSaver {
    @Autowired
    private DataUpdate dataUpdate;
    @Autowired
    private PhoneIntervalService intervalService;

    @Override
    public void save(IntervalsCleaner intervalsCleaner) {
        List<PhoneInterval> intervalList = dataUpdate.getPhoneIntervals().poll();
        if (intervalList != null) {
            intervalsCleaner.clean();
        }
        while (intervalList != null) {
            intervalService.addAll(intervalList);
            intervalList = dataUpdate.getPhoneIntervals().poll();
        }
    }


}
