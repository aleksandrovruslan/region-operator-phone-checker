package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneRegion;

import java.util.Collection;
import java.util.List;

public interface PhoneRegionService {

    List<PhoneRegion> getRegions();

    PhoneRegion getRegion(Integer id);

    PhoneRegion saveRegion(PhoneRegion region);

    PhoneRegion updateRegion(PhoneRegion region, Integer id);

    void saveRegions(Collection<PhoneRegion> regions);

    void deleteRegion(PhoneRegion region);

    List<PhoneRegion> searchRegions(String searchString);

}
