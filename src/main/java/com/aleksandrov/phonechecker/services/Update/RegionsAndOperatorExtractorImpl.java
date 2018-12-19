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
        findOperators();
        dataUpdate.getUpdateStatus().add("Phone operators fined " + new Date());
        findRegions();
        dataUpdate.getUpdateStatus().add("Phone regions fined " + new Date());
    }

    private void findOperators() {
        try {
            operatorService.getOperators().forEach((operator) ->
                    dataUpdate.getOperators().put(operator.getName(), operator));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findRegions() {
        try {
            regionService.getRegions().forEach(region ->
                    dataUpdate.getRegions().put(region.getName(), region));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
