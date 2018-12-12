package com.aleksandrov.phonechecker.services.Update;

import com.aleksandrov.phonechecker.models.PhoneInterval;

import java.util.List;

public interface IntervalsSaver {
    void save(List<PhoneInterval> intervals);
}
