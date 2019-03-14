package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneInterval;

import java.util.List;

public interface PhoneIntervalService {

    List<PhoneInterval> getIntervals();

    void addPhoneInterval(PhoneInterval interval);

    void addAll(Iterable<PhoneInterval> intervals);

    void deleteAll();

}
