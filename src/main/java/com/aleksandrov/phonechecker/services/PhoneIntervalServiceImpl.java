package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.repositories.PhoneIntervalDAO;
import com.aleksandrov.phonechecker.repositories.PhoneOperatorDAO;
import com.aleksandrov.phonechecker.repositories.PhoneRegionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class PhoneIntervalServiceImpl implements PhoneIntervalService {
    @Autowired
    private PhoneIntervalDAO intervalDAO;

    @Override
    public List<PhoneInterval> getIntervals() {
        return intervalDAO.findAll();
    }

    @Override
    @Transactional
    public void addPhoneInterval(PhoneInterval interval) {
        intervalDAO.save(interval);
    }

    @Override
    public void addAll(Set<PhoneInterval> intervals) {
        intervalDAO.saveAll(intervals);
    }

    @Override
    public void deleteAll() {
        intervalDAO.deleteAll();
    }
}
