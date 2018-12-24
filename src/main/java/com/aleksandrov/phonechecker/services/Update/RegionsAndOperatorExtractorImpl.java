package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.services.PhoneOperatorService;
import com.aleksandrov.phonechecker.services.PhoneRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegionsAndOperatorExtractorImpl implements RegionsAndOperatorExtractor {
    @Autowired
    private DataUpdate dataUpdate;
    @Autowired
    private PhoneOperatorService operatorService;
    @Autowired
    private PhoneRegionService regionService;

    @Override
    public void extract() {
        operatorService.getOperators().forEach((operator) ->
                dataUpdate.getOperators().put(operator.getName(), operator));
        dataUpdate.getUpdateStatus().add("Phone operators fined " + new Date());
        regionService.getRegions().forEach(region ->
                dataUpdate.getRegions().put(region.getName(), region));
        dataUpdate.getUpdateStatus().add("Phone regions fined " + new Date());
    }
}
