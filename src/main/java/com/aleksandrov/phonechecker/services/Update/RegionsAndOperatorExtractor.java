package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public interface RegionsAndOperatorExtractor {
    void extract(List<String> updateStatusList,
                 BlockingQueue<List<String>> rawIntervalsStrings,
                 BlockingQueue<List<PhoneInterval>> intervals,
                 ConcurrentMap<String, PhoneOperator> operators,
                 ConcurrentMap<String, PhoneRegion> regions,
                 Runnable endUpdateState);
}
