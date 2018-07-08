package com.aleksandrov.phonechecker.repositories;

import com.aleksandrov.phonechecker.models.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberDAO extends JpaRepository<PhoneNumber, Integer> {
    PhoneNumber findFirstByPrefixEqualsAndNumberEquals(String prefix, String number);
}
