package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.models.PhoneOperator;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.services.PhoneOperatorService;
import com.aleksandrov.phonechecker.services.PhoneRegionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RawStringHandlerImpl implements RawStringHandler {

    @Autowired
    private DataUpdate dataUpdate;
    @Autowired
    private PhoneOperatorService operatorService;
    @Autowired
    private PhoneRegionService regionService;

    private static final Logger log = LogManager.getLogger(RawStringHandlerImpl.class);

    //301;2100000;2109999;10000;ОАО "АСВТ";г. Улан - Удэ|Республика Бурятия
    private final String stringTemplate = "АВС/ DEF;От;До;Емкость;Оператор;Регион";
    private final int prefixIndex = 0;
    private final int startIntervalIndex = 1;
    private final int endIntervalIndex = 2;
    private final int operatorIndex = 4;
    private final int regionIndex = 5;

    @Override
    public void perform() {
        dataUpdate.getUpdateStatus().add("In RawStringHandler " + new Date());
        dataUpdate.getUpdateStatus().add("Start RawStringHandler.perform() " + new Date());
        List<String> rawStringList = dataUpdate.getRawDownloadsString().poll();
        while (rawStringList != null){
            if (!stringTemplate.equalsIgnoreCase(rawStringList.get(0))) {
                log.error(rawStringList.get(0));
                throw new IllegalStateException("Incorrect template!");
            }
            rawStringList.remove(0);
            parseIntervals(rawStringList);
            rawStringList = dataUpdate.getRawDownloadsString().poll();
        }
    }

    private void parseIntervals(List<String> rawStringList) {
        List<PhoneInterval> intervalList = new ArrayList<>(rawStringList.size());
        for (String rawString : rawStringList) {
            String[] rawInterval = rawString.split(";");
            PhoneOperator operator = dataUpdate.getOperators().get(rawInterval[operatorIndex]);
            if (operator == null) {
                operator = new PhoneOperator(rawInterval[operatorIndex]);
                operator = operatorService.saveOperator(operator);
                dataUpdate.getOperators().put(operator.getName(), operator);
            }

            PhoneRegion region = dataUpdate.getRegions().get(rawInterval[regionIndex]);
            if (region == null) {
                region = new PhoneRegion(rawInterval[regionIndex]);
                region = regionService.saveRegion(region);
                dataUpdate.getRegions().put(region.getName(), region);
            }

            intervalList.add(new PhoneInterval(rawInterval[prefixIndex],
                    rawInterval[startIntervalIndex], rawInterval[endIntervalIndex],
                    operator, region));
        }
        dataUpdate.getPhoneIntervals().add(intervalList);
    }

}
