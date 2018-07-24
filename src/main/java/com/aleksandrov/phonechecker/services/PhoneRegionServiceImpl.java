package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.repositories.PhoneRegionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PhoneRegionServiceImpl implements PhoneRegionService {
    @Autowired
    private PhoneRegionDAO regionDAO;

    @Override
    public List<PhoneRegion> getRegions() {
        return regionDAO.findAll();
    }

    @Override
    public PhoneRegion getRegion(int id) {
        return regionDAO.getOne(id);
    }

    @Override
    public PhoneRegion saveRegion(PhoneRegion region) {
        return regionDAO.save(region);
    }

    @Override
    public PhoneRegion updateRegion(PhoneRegion region) {
        return null;
    }

    @Override
    public void saveRegions(Collection<PhoneRegion> regions) {
        regionDAO.saveAll(regions);
    }

    @Override
    public void deleteRegion(PhoneRegion region) {
        regionDAO.delete(region);
    }
}
