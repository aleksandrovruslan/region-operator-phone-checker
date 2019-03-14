package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneInterval;
import com.aleksandrov.phonechecker.repositories.PhoneIntervalDAO;
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
    public void addAll(Iterable<PhoneInterval> intervals) {
        intervalDAO.saveAll(intervals);
    }

    @Override
    public void deleteAll() {
        intervalDAO.deleteAll();
    }

}
