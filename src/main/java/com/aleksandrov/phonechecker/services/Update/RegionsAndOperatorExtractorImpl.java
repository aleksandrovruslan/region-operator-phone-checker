package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.services.PhoneOperatorService;
import com.aleksandrov.phonechecker.services.PhoneRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

@Service
public class RegionsAndOperatorExtractorImpl implements RegionsAndOperatorExtractor {
    @Autowired
    private PhoneOperatorService operatorService;
    @Autowired
    private PhoneRegionService regionService;
    @Autowired
    private RawStringHandler rawStringHandler;

    @Override
    public void extract(List<String> updateStatusList,
                        BlockingQueue<List<String>> rawIntervalsStrings,
                        BlockingQueue<List<PhoneInterval>> intervals,
                        ConcurrentMap<String, PhoneOperator> operators,
                        ConcurrentMap<String, PhoneRegion> regions,
                        Runnable endUpdateState) {
        findOperators(operators);
        updateStatusList.add("Phone operators fined " + new Date());
        findRegions(regions);
        updateStatusList.add("Phone regions fined " + new Date());
        rawStringHandler.perform(updateStatusList, rawIntervalsStrings,
                intervals, operators, regions, endUpdateState);
    }

    private void findOperators(ConcurrentMap<String, PhoneOperator> operators) {
        try {
            operatorService.getOperators().forEach((operator) -> operators.put(operator.getName(), operator));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findRegions(ConcurrentMap<String, PhoneRegion> regions) {
        try {
            regionService.getRegions().forEach(region -> regions.put(region.getName(), region));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
