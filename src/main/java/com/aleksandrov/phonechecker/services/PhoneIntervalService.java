package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneInterval;

import java.util.List;
import java.util.Set;

public interface PhoneIntervalService {

    List<PhoneInterval> getIntervals();

    void addPhoneInterval(PhoneInterval interval);

    void addAll(Set<PhoneInterval> intervals);

    void deleteAll();
}
