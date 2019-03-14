package com.aleksandrov.phonechecker.services;

import com.aleksandrov.phonechecker.errors.RegionNotFoundException;
import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.repositories.PhoneRegionDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
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
    public PhoneRegion getRegion(Integer id) {
        return regionDAO.findById(id)
                .orElseThrow(() -> new RegionNotFoundException(id.toString()));
    }

    @Override
    public PhoneRegion saveRegion(PhoneRegion region) {
        return regionDAO.save(region);
    }

    @Override
    public PhoneRegion updateRegion(@NotNull PhoneRegion region, @NotNull Integer id) {
        getRegion(id);
        return regionDAO.save(region);
    }

    @Override
    public void saveRegions(Collection<PhoneRegion> regions) {
        regionDAO.saveAll(regions);
    }

    @Override
    public void deleteRegion(PhoneRegion region) {
        regionDAO.delete(region);
    }

    @Override
    public List<PhoneRegion> searchRegions(String searchString) {
        return regionDAO.findAllByNameContainingIgnoreCase(searchString);
    }

}
