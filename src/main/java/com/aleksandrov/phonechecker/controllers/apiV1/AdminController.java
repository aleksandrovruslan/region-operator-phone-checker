package com.aleksandrov.phonechecker.controllers.apiV1;

import com.aleksandrov.phonechecker.models.PhoneRegion;
import com.aleksandrov.phonechecker.services.Downloader;
import com.aleksandrov.phonechecker.services.PhoneRegionService;
import com.aleksandrov.phonechecker.services.ReaderCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(AdminController.ADMIN_MAPPING)
public class AdminController {
    public static final String ADMIN_MAPPING = "/api/v1/admin";
    public static final String UPDATE_BASE = "/update";
    public static final String UPDATE_REGION = "/region/";
    @Autowired
    private Downloader downloader;
    @Autowired
    private ReaderCSV readerCSV;
    @Autowired
    private PhoneRegionService regionService;

    // TODO make a check by login and password, or automatically check changes on the site https://rossvyaz.ru
    @GetMapping(UPDATE_BASE)
    public String update() {
        try {
            downloader.download();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readerCSV.read();
    }

    @PutMapping(UPDATE_REGION)
    public PhoneRegion regionUpdate(PhoneRegion region) {
        return regionService.saveRegion(region);
    }
}