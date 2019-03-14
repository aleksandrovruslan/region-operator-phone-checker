package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.services.PhoneIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntervalsCleaner {

    @Autowired
    private PhoneIntervalService intervalService;

    private boolean cleaned;

    void clean() {
        if (!cleaned) {
            synchronized (this) {
                if (!cleaned) {
                    cleaned = true;
                    intervalService.deleteAll();
                }
            }
        }
    }

}
