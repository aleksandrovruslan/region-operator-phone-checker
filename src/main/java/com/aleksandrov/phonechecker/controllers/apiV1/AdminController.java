package com.aleksandrov.phonechecker.controllers.apiV1;

import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.services.Downloader;
import com.aleksandrov.phonechecker.services.PhoneRegionService;
import com.aleksandrov.phonechecker.services.ReaderCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(AdminController.ADMIN_MAPPING)
public class AdminController {
    public static final String ADMIN_MAPPING = "/api/v1/admin";
    public static final String UPDATE_BASE = "/update";
    public static final String UPDATE_REGION = "/region/{id}";
    public static final String GET_REGION = "/region/{id}";
    public static final String SEARCH_REGIONS = "/region/search/{searchString}";

    @Autowired
    private Downloader downloader;
    @Autowired
    private ReaderCSV readerCSV;
    @Autowired
    private PhoneRegionService regionService;

    // TODO make a check by login and password, or automatically check changes on the site https://rossvyaz.ru
    @GetMapping(UPDATE_BASE)
    public ResponseEntity<?> update() {
        try {
            downloader.download();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readerCSV.read();
        return ResponseEntity.ok("update successfully");
    }

    @PutMapping(UPDATE_REGION)
    public ResponseEntity<?> regionUpdate(@RequestBody PhoneRegion region, @PathVariable("id") Integer id) {
        regionService.updateRegion(region, id);
        return ResponseEntity.ok("resource saved");
    }

    @GetMapping(SEARCH_REGIONS)
    List<PhoneRegion> searchRegions(@PathVariable String searchString) {
        return regionService.searchRegions(searchString);
    }

    @GetMapping(GET_REGION)
    public  PhoneRegion getRegion(@PathVariable Integer id) {
        return regionService.getRegion(id);
    }
}