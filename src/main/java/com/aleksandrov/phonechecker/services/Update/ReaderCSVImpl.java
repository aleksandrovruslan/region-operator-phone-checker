package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.services.PhoneIntervalService;
import com.aleksandrov.phonechecker.services.PhoneOperatorService;
import com.aleksandrov.phonechecker.services.PhoneRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Component(value = "reader")
public class ReaderCSVImpl implements ReaderCSV {
    private static final Logger log = LoggerFactory.getLogger(ReaderCSVImpl.class);
//    private static final String FILE_PATH = DownloaderImpl.SAVE_PATH;
//    private static final String[] files = {DownloaderImpl.INTERVAL1_CSV
//            , DownloaderImpl.INTERVAL2_CSV, DownloaderImpl.INTERVAL3_CSV
//            , DownloaderImpl.INTERVAL4_CSV};
private static final String FILE_PATH = null;
    private static final String[] files = {null};
    @Autowired
    private PhoneIntervalService intervalService;
    @Autowired
    private PhoneOperatorService operatorService;
    @Autowired
    private PhoneRegionService regionService;

    @Override
    public String read() {
        intervalService.deleteAll();
        String charsetNameW = "windows-1251";

        PhoneOperator operator;
        PhoneRegion region;
        Set<PhoneInterval> intervals = new HashSet<>();
//        Map<String, PhoneOperator> operatorsMap = getOperators();
//        Map<String, PhoneRegion> regionsMap = getRegions();
        Map<String, PhoneOperator> operatorsMap = null;
        Map<String, PhoneRegion> regionsMap = null;

        for (String path:files) {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(FILE_PATH + path), charsetNameW))) {

                br.readLine();
                String str;
                while ((str = br.readLine()) != null) {
                    String[] strArr = str.split(";");
                    operator = operatorsMap.get(strArr[4]);
                    if (operator == null) {
                        operator = new PhoneOperator(strArr[4]);
                        operator = operatorService.saveOperator(operator);
                        operatorsMap.put(operator.getName(), operator);
                    }

                    region = regionsMap.get(strArr[5]);
                    if (region == null) {
                        region = new PhoneRegion(strArr[5]);
                        region = regionService.saveRegion(region);
                        regionsMap.put(region.getName(), region);
                    }
                    intervals.add(new PhoneInterval(strArr[0]
                            , strArr[1], strArr[2]
                            , operator, region));
                }
                intervalService.addAll(intervals);
            } catch (IOException e) {
                log.error("ReaderCSVImpl read(): " + e);
            }
        }
        return "Updated successfully.";
    }

//    private Map<String, PhoneOperator> getOperators() {
//        Map<String, PhoneOperator> operators = new HashMap<>();
//        try {
//            operatorService.getOperators().forEach((operator) -> operators.put(operator.getName(), operator));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return operators;
//    }
//
//    private Map<String, PhoneRegion> getRegions() {
//        Map<String, PhoneRegion> regionMap = new HashMap<>();
//        try {
//            regionService.getRegions().forEach(region -> regionMap.put(region.getName(), region));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return regionMap;
//    }
}
