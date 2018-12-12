package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.services.PhoneIntervalService;
import com.aleksandrov.phonechecker.services.PhoneOperatorService;
import com.aleksandrov.phonechecker.services.PhoneRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

@Service
public class RawStringHandlerImpl implements RawStringHandler {
    //301;2100000;2109999;10000;ОАО "АСВТ";г. Улан - Удэ|Республика Бурятия
    private final String stringTemplate = "АВС/ DEF;От;До;Емкость;Оператор;Регион";
    private int prefixIndex = 0;
    private int startIntervalIndex = 1;
    private int endIntervalIndex = 2;
    private int operatorIndex = 4;
    private int regionIndex = 5;

    private BlockingQueue<List<String>> rawIntervalsStrings;
    private BlockingQueue<List<PhoneInterval>> intervals;
    private ConcurrentMap<String, PhoneOperator> operators;
    private ConcurrentMap<String, PhoneRegion> regions;

    @Autowired
    private PhoneOperatorService operatorService;
    @Autowired
    private PhoneRegionService regionService;
    @Autowired
    private PhoneIntervalService intervalService;

    @Override
    public void perform(List<String> updateStatusList,
                        BlockingQueue<List<String>> rawIntervalsStrings,
                        BlockingQueue<List<PhoneInterval>> intervals,
                        ConcurrentMap<String, PhoneOperator> operators,
                        ConcurrentMap<String, PhoneRegion> regions,
                        Runnable endUpdateState) {
        updateStatusList.add("In RawStringHandler " + new Date());
        this.rawIntervalsStrings = rawIntervalsStrings;
        this.intervals = intervals;
        this.operators = operators;
        this.regions = regions;
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateStatusList.add("Start RawStringHandler.perform() " + new Date());
        for (List<String> list : rawIntervalsStrings) {
            if (!stringTemplate.equalsIgnoreCase(list.get(0))) {
                throw new IllegalStateException("Incorrect template!");
            }
            list.remove(0);
            parseIntervals(list);
        }
        //in this point save intervals to base

        updateStatusList.add("Deleted all intervals in base " + new Date());
        intervalService.deleteAll();
        List<PhoneInterval> intervalList = intervals.poll();
        while (intervalList != null) {
            intervalService.addAll(intervalList);
            intervalList = intervals.poll();
        }
        updateStatusList.add("Update completed  " + new Date());
        endUpdateState.run();
    }

    private void parseIntervals(List<String> list) {
        List<PhoneInterval> intervalList = new ArrayList<>(list.size());
        for (String rawString : list) {
            String[] rawInterval = rawString.split(";");
            PhoneOperator operator = operators.get(rawInterval[operatorIndex]);
            if (operator == null) {
                operator = new PhoneOperator(rawInterval[operatorIndex]);
                operator = operatorService.saveOperator(operator);

                //not multithreading
                operators.put(operator.getName(), operator);
            }

            PhoneRegion region = regions.get(rawInterval[regionIndex]);
            if (region == null) {
                region = new PhoneRegion(rawInterval[regionIndex]);
                region = regionService.saveRegion(region);

                //not multithreading
                regions.put(region.getName(), region);
            }

            intervalList.add(new PhoneInterval(rawInterval[prefixIndex],
                    rawInterval[startIntervalIndex], rawInterval[endIntervalIndex],
                    operator, region));
        }
        intervals.add(intervalList);
    }
}
