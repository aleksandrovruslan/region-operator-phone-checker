package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneNumber;

public interface CheckService {
    PhoneNumber check(String id);
}
