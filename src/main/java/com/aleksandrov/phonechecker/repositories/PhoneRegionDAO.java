package com.aleksandrov.phonechecker.repositories;

import com.aleksandrov.phonechecker.models.PhoneRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRegionDAO extends JpaRepository<PhoneRegion, Integer> {
}
