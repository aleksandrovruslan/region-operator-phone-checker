package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component(value = "reader")
public class ReaderCSVImpl implements ReaderCSV {
    private static final String FILE_PATH = DownloaderImpl.SAVE_PATH;
    private static final String[] files = {DownloaderImpl.INTERVAL1_CSV
            , DownloaderImpl.INTERVAL2_CSV, DownloaderImpl.INTERVAL3_CSV
            , DownloaderImpl.INTERVAL4_CSV};
    @Autowired
    private PhoneIntervalService intervalService;
    @Autowired
    private PhoneOperatorService operatorService;
    @Autowired
    private PhoneRegionService regionService;

    //TODO divided into small methods
    @Override
    public String read() {
        BufferedReader br;
        String charsetNameW = "windows-1251";

        PhoneOperator operator;
        PhoneRegion region;
        Set<PhoneInterval> intervals = new HashSet<>();
        Map<String, PhoneOperator> operatorsMap = new HashMap<>();
        Map<String, PhoneRegion> regionsMap = new HashMap<>();

        try {
            for (String path:files) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH + path), charsetNameW));
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
                    intervals.add(new PhoneInterval(Integer.parseInt(strArr[0])
                            , Integer.parseInt(strArr[1]), Integer.parseInt(strArr[2])
                            , operator, region));
                }
            }
            intervalService.addAll(intervals);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Updated successfully.";
    }
}
