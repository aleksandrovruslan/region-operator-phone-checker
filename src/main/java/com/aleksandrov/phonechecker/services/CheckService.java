package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneNumber;

import java.util.List;

public interface CheckService {
    List<PhoneNumber> check(String id);
}
