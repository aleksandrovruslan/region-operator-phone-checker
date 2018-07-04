package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneNumber;

import java.util.List;

public interface CheckService {
    PhoneNumber check(PhoneNumber number);

    List<PhoneNumber> checkAll(List<PhoneNumber> numbers);
}
